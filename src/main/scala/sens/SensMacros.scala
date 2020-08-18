/*
 * Copyright (c) 2020.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package sens
import scala.annotation.tailrec
import scala.reflect.macros.blackbox

/**
  * SensMacros
  *
  * @author zhaihao
  * @version 1.0
  * @since 2020/8/18 22:50
  */
//noinspection ScalaUnusedSymbol
object SensMacros {
  private val ShapeInfo = "Path must have shape: _.field1.field2.each.field3.(...)"
  def modify_impl[T: c.WeakTypeTag, U: c.WeakTypeTag](c: blackbox.Context)(obj: c.Expr[T])(
      path: c.Expr[T => U]
  ): c.Tree = modifyUnwrapped(c)(obj, modificationForPath(c)(path))

  def modifyAll_impl[T: c.WeakTypeTag, U: c.WeakTypeTag](c: blackbox.Context)(obj: c.Expr[T])(
      path1: c.Expr[T => U],
      paths: c.Expr[T => U]*
  ): c.Tree = modifyUnwrapped(c)(obj, modificationsForPaths(c)(path1, paths))

  private def modifyUnwrapped[T: c.WeakTypeTag, U: c.WeakTypeTag](c: blackbox.Context)(
      obj:           c.Expr[T],
      modifications: c.Tree
  ): c.Tree = {
    import c.universe._
    q"_root_.sens.PathModify($obj, $modifications)"
  }

  def modifyLazy_impl[T: c.WeakTypeTag, U: c.WeakTypeTag](c: blackbox.Context)(
      path: c.Expr[T => U]
  ): c.Tree = modifyLazyUnwrapped(c)(modificationForPath(c)(path))

  def modifyLazyAll_impl[T: c.WeakTypeTag, U: c.WeakTypeTag](c: blackbox.Context)(
      path1: c.Expr[T => U],
      paths: c.Expr[T => U]*
  ): c.Tree = modifyLazyUnwrapped(c)(modificationsForPaths(c)(path1, paths))

  private def modifyLazyUnwrapped[T: c.WeakTypeTag, U: c.WeakTypeTag](c: blackbox.Context)(
      modifications: c.Tree
  ): c.Tree = {
    import c.universe._
    q"_root_.sens.PathLazyModify($modifications)"
  }

  def modifyPimp_impl[T: c.WeakTypeTag, U: c.WeakTypeTag](c: blackbox.Context)(
      path: c.Expr[T => U]
  ): c.Tree = modifyWrapped(c)(modificationForPath(c)(path))

  def modifyAllPimp_impl[T: c.WeakTypeTag, U: c.WeakTypeTag](c: blackbox.Context)(
      path1: c.Expr[T => U],
      paths: c.Expr[T => U]*
  ): c.Tree = modifyWrapped(c)(modificationsForPaths(c)(path1, paths))

  def modifyWrapped[T: c.WeakTypeTag, U: c.WeakTypeTag](c: blackbox.Context)(
      modifications: c.Tree
  ): c.Tree = {
    import c.universe._

    val wrappedValue = c.macroApplication match {
      case Apply(TypeApply(Select(Apply(_, List(w)), _), _), _) => w
      case _                                                    => c.abort(c.enclosingPosition, s"Unknown usage of ModifyPimp. Please file a bug.")
    }

    val valueAlias = TermName(c.freshName())

    q"""{
      val $valueAlias = $wrappedValue;
      _root_.sens.PathModify(${Ident(valueAlias)}, $modifications)
     }"""
  }

  private def modificationsForPaths[T: c.WeakTypeTag, U: c.WeakTypeTag](c: blackbox.Context)(
      path1: c.Expr[T => U],
      paths: Seq[c.Expr[T => U]]
  ): c.Tree = {
    import c.universe._

    val valueName    = TermName(c.freshName())
    val modifierName = TermName(c.freshName())

    val modification1 = q"${modificationForPath(c)(path1)}($valueName, $modifierName)"
    val chained = paths.foldLeft(modification1) {
      case (tree, path) =>
        val modification = modificationForPath(c)(path)
        q"$modification($tree, $modifierName)"
    }

    val valueArg    = q"val $valueName: ${weakTypeOf[T]}"
    val modifierArg = q"val $modifierName: (${weakTypeOf[U]} => ${weakTypeOf[U]})"
    q"($valueArg, $modifierArg) => $chained"
  }

  private def modificationForPath[T: c.WeakTypeTag, U: c.WeakTypeTag](c: blackbox.Context)(
      path: c.Expr[T => U]
  ): c.Tree = {
    import c.universe._

    sealed trait PathAccess
    case object DirectPathAccess extends PathAccess
    case class SealedPathAccess(types: Set[Symbol]) extends PathAccess

    sealed trait PathElement
    case class TermPathElement(term:       c.TermName, access: PathAccess, xargs: c.Tree*) extends PathElement
    case class SubtypePathElement(subtype: c.Symbol) extends PathElement
    case class FunctorPathElement(functor: c.Tree, method: c.TermName, xargs: c.Tree*) extends PathElement

    def determinePathAccess(typeSymbol: Symbol): PathAccess = {
      def ifEmpty[A](set: Set[A], empty: => Set[A]) =
        if (set.isEmpty) empty else set

      def knownDirectSubclasses(sealedSymbol: ClassSymbol) = ifEmpty(
        sealedSymbol.knownDirectSubclasses,
        c.abort(
          c.enclosingPosition,
          s"""Could not find subclasses of sealed trait $sealedSymbol.
             |You might need to ensure that it gets compiled before this invocation.
             |See also: <https://issues.scala-lang.org/browse/SI-7046>.""".stripMargin
        )
      )

      def expand(symbol: Symbol): Set[Symbol] =
        Set(symbol)
          .filter(_.isClass)
          .map(_.asClass)
          .map { s =>
            s.typeSignature; s
          }
          .filter(_.isSealed)
          .flatMap(s => knownDirectSubclasses(s))
          .flatMap(s => ifEmpty(expand(s), Set(s)))

      val subclasses = expand(typeSymbol)
      if (subclasses.isEmpty) DirectPathAccess else SealedPathAccess(subclasses)
    }

    @tailrec
    def collectPathElements(tree: c.Tree, acc: List[PathElement]): List[PathElement] = {
      def methodSupported(method: TermName) =
        Seq("at", "eachWhere", "atOrElse", "index").contains(method.toString)
      def typeSupported(sensType: c.Tree) =
        Seq("SensEach", "SensAt", "SensMapAt", "SensWhen", "SensEither", "SensSingleAt")
          .exists(sensType.toString.endsWith)
      tree match {
        case q"$parent.$child" =>
          val access = determinePathAccess(parent.tpe.typeSymbol)
          collectPathElements(parent, TermPathElement(child, access) :: acc)
        case q"$tpname[..$_]($parent).when[$tp]" if typeSupported(tpname) =>
          collectPathElements(parent, SubtypePathElement(tp.tpe.typeSymbol) :: acc)
        case q"$parent.$method(..$xargs)" if methodSupported(method) =>
          collectPathElements(parent, TermPathElement(method, DirectPathAccess, xargs: _*) :: acc)
        case q"$tpname[..$_]($t)($f)" if typeSupported(tpname) =>
          val newAcc = (acc: @unchecked) match {
            case TermPathElement(term, _, xargs @ _*) :: rest => FunctorPathElement(f, term, xargs: _*) :: rest
            case pathEl :: _ =>
              c.abort(c.enclosingPosition, s"Invalid use of path element $pathEl. $ShapeInfo, got: ${path.tree}")
          }
          collectPathElements(t, newAcc)
        case _: Ident => acc
        case _ => c.abort(c.enclosingPosition, s"Unsupported path element. $ShapeInfo, got: $tree")
      }
    }

    def generateSelects(rootPathEl: c.TermName, reversePathEls: List[PathElement]): c.Tree = {
      @tailrec
      def terms(els: List[PathElement], result: List[c.TermName]): List[c.TermName] =
        els match {
          case Nil                               => result
          case TermPathElement(term, _) :: tail  => terms(tail, term :: result)
          case SubtypePathElement(_) :: _        => result
          case FunctorPathElement(_, _, _*) :: _ => result
        }

      @tailrec
      def go(els: List[c.TermName], result: c.Tree): c.Tree =
        els match {
          case Nil => result
          case pathEl :: tail =>
            val select = q"$result.$pathEl"
            go(tail, select)
        }

      go(terms(reversePathEls, Nil), Ident(rootPathEl))
    }

    def generateAccess(tree: c.Tree, access: PathAccess)(f: c.Tree => c.Tree) = access match {
      case DirectPathAccess => f(tree)
      case SealedPathAccess(types) =>
        val cases = types map { tp =>
          val pat = TermName(c.freshName())
          cq"$pat: $tp => ${f(Ident(pat))}"
        }
        q"$tree match { case ..$cases }"
    }

    @tailrec
    def generateCopies(
        rootPathEl:     c.TermName,
        reversePathEls: List[PathElement],
        newVal:         c.Tree
    ): (c.TermName, c.Tree) =
      reversePathEls match {
        case Nil => (rootPathEl, newVal)
        case TermPathElement(pathEl, access) :: tail =>
          val selectCurrVal = generateSelects(rootPathEl, tail)
          val copy = generateAccess(selectCurrVal, access) { currVal =>
            q"$currVal.copy($pathEl = $newVal)"
          }
          generateCopies(rootPathEl, tail, copy)
        case SubtypePathElement(subtype) :: tail =>
          val newRootPathEl = TermName(c.freshName())
          val intactPathEl  = TermName(c.freshName())
          val selectCurrVal = generateSelects(newRootPathEl, tail)
          val cases = Seq(
            cq"$rootPathEl: $subtype => $newVal",
            cq"$intactPathEl => ${Ident(intactPathEl)}"
          )
          val modifySubtype = q"$selectCurrVal match { case ..$cases }"
          generateCopies(newRootPathEl, tail, modifySubtype)
        case FunctorPathElement(functor, method, xargs @ _*) :: tail =>
          val newRootPathEl = TermName(c.freshName())
          val args                = generateSelects(newRootPathEl, tail) :: xargs.toList
          val rootPathElParamTree = ValDef(Modifiers(), rootPathEl, TypeTree(), EmptyTree)
          val functorMap          = q"$functor.$method(..$args)(($rootPathElParamTree) => $newVal)"
          generateCopies(newRootPathEl, tail, functorMap)
      }

    val pathEls = path.tree match {
      case q"($arg) => $pathBody" => collectPathElements(pathBody, Nil)
      case _                      => c.abort(c.enclosingPosition, s"$ShapeInfo, got: ${path.tree}")
    }

    val initialRootPathEl = TermName(c.freshName())
    val fn                = TermName(c.freshName())

    val reversePathEls = pathEls.reverse

    val select = generateSelects(initialRootPathEl, reversePathEls)
    val mod    = q"$fn($select)"

    val (rootPathEl, copies) = generateCopies(initialRootPathEl, reversePathEls, mod)

    val rootPathElParamTree = q"val $rootPathEl: ${weakTypeOf[T]}"
    val fnParamTree         = q"val $fn: (${weakTypeOf[U]} => ${weakTypeOf[U]})"

    q"($rootPathElParamTree, $fnParamTree) => $copies"
  }
}
