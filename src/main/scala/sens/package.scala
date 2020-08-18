/*
 * Copyright (c) 2020.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

import scala.annotation.compileTimeOnly
import scala.collection.TraversableLike
import scala.collection.SeqLike
import scala.collection.generic.CanBuildFrom
import scala.language.experimental.macros
import scala.language.higherKinds

/**
  * package
  *
  * @author zhaihao
  * @version 1.0
  * @since 2020/8/18 22:54
  */
//noinspection ScalaUnusedSymbol
package object sens {
  private def canOnlyBeUsedInsideModify(method: String) =
    s"$method can only be used inside modify"

  def modify[T, U](obj: T)(path: T => U): PathModify[T, U] = macro SensMacros.modify_impl[T, U]

  def modifyAll[T, U](obj: T)(path1: T => U, paths: (T => U)*): PathModify[T, U] = macro SensMacros.modifyAll_impl[T, U]

  implicit class ModifyPimp[T](t: T) {
    def modify[U](path:     T => U): PathModify[T, U] = macro SensMacros.modifyPimp_impl[T, U]
    def modifyAll[U](path1: T => U, paths: (T => U)*): PathModify[T, U] = macro SensMacros.modifyAllPimp_impl[T, U]
  }

  case class PathModify[T, U](obj: T, doModify: (T, U => U) => T) {

    def using(mod: U => U): T = doModify(obj, mod)

    final def apply(mod: U => U): T = using(mod)

    def usingIf(condition: Boolean)(mod: U => U): T = if (condition) doModify(obj, mod) else obj

    def setTo(v: U): T = doModify(obj, _ => v)

    def setToIfDefined(v: Option[U]): T = v.fold(obj)(setTo)

    def setToIf(condition: Boolean)(v: => U): T = if (condition) setTo(v) else obj
  }

  implicit class AbstractPathModifyPimp[T, U](f1: T => PathModify[T, U]) {
    def andThenModify[V](f2: U => PathModify[U, V]): T => PathModify[T, V] = { t: T =>
      PathModify[T, V](t, (t, vv) => f1(t).doModify(t, u => f2(u).doModify(u, vv)))
    }
  }

  def modify[T]: LensHelper[T] = LensHelper[T]()

  def modifyAll[T]: MultiLensHelper[T] = MultiLensHelper[T]()

  case class LensHelper[T] private () {

    def apply[U](path: T => U): PathLazyModify[T, U] = macro SensMacros.modifyLazy_impl[T, U]
  }

  case class MultiLensHelper[T] private () {

    def apply[U](path1: T => U, paths: (T => U)*): PathLazyModify[T, U] = macro SensMacros.modifyLazyAll_impl[T, U]
  }

  case class PathLazyModify[T, U](doModify: (T, U => U) => T) { self =>

    def using(mod: U => U): T => T = obj => doModify(obj, mod)

    def usingIf(condition: Boolean)(mod: U => U): T => T =
      obj =>
        if (condition) doModify(obj, mod)
        else obj

    def setTo(v: U): T => T = obj => doModify(obj, _ => v)

    def setToIfDefined(v: Option[U]): T => T = v.fold((obj: T) => obj)(setTo)

    def setToIf(condition: Boolean)(v: => U): T => T =
      if (condition) setTo(v)
      else obj => obj

    def andThenModify[V](f2: PathLazyModify[U, V]): PathLazyModify[T, V] =
      PathLazyModify[T, V]((t, vv) => self.doModify(t, u => f2.doModify(u, vv)))
  }

  implicit class SensEach[F[_], T](t: F[T])(implicit f: SensFunctor[F, T]) {
    @compileTimeOnly(canOnlyBeUsedInsideModify("each"))
    def each: T = sys.error("")

    @compileTimeOnly(canOnlyBeUsedInsideModify("eachWhere"))
    def eachWhere(p: T => Boolean): T = sys.error("")
  }

  trait SensFunctor[F[_], A] {
    def map(fa:       F[A])(f: A => A): F[A]
    def each(fa:      F[A])(f: A => A): F[A] = map(fa)(f)
    def eachWhere(fa: F[A], p: A => Boolean)(f: A => A): F[A] = map(fa) { a =>
      if (p(a)) f(a) else a
    }
  }

  implicit def optionSensFunctor[A]: SensFunctor[Option, A] with SensSingleAtFunctor[Option, A] =
    new SensFunctor[Option, A] with SensSingleAtFunctor[Option, A] {
      override def map(fa:      Option[A])(f:       A => A): Option[A] = fa.map(f)
      override def at(fa:       Option[A])(f:       A => A): Option[A] = Some(fa.map(f).get)
      override def atOrElse(fa: Option[A], default: => A)(f: A => A): Option[A] = fa.orElse(Some(default)).map(f)
      override def index(fa:    Option[A])(f:       A => A): Option[A] = fa.map(f)
    }

  trait SensSingleAtFunctor[F[_], T] {
    def at(fa:       F[T])(f:       T => T): F[T]
    def atOrElse(fa: F[T], default: => T)(f: T => T): F[T]
    def index(fa:    F[T])(f:       T => T): F[T]
  }

  implicit class SensSingleAt[F[_], T](t: F[T])(implicit f: SensSingleAtFunctor[F, T]) {
    @compileTimeOnly(canOnlyBeUsedInsideModify("at"))
    def at: T = sys.error("")

    @compileTimeOnly(canOnlyBeUsedInsideModify("atOrElse"))
    def atOrElse(default: => T): T = sys.error("")

    @compileTimeOnly(canOnlyBeUsedInsideModify("index"))
    def index: T = sys.error("")
  }

  implicit def traversableSensFunctor[F[_], A](
      implicit cbf: CanBuildFrom[F[A], A, F[A]],
      ev:           F[A] => TraversableLike[A, F[A]]
  ): SensFunctor[F, A] =
    new SensFunctor[F, A] {
      override def map(fa: F[A])(f: A => A) = fa.map(f)
    }

  implicit class SensAt[F[_], T](t: F[T])(implicit f: SensAtFunctor[F, T]) {
    @compileTimeOnly(canOnlyBeUsedInsideModify("at"))
    def at(idx: Int): T = sys.error("")

    @compileTimeOnly(canOnlyBeUsedInsideModify("index"))
    def index(idx: Int): T = sys.error("")
  }

  trait SensAtFunctor[F[_], T] {
    def at(fa:    F[T], idx: Int)(f: T => T): F[T]
    def index(fa: F[T], idx: Int)(f: T => T): F[T]
  }

  implicit class SensMapAt[M[KT, TT] <: Map[KT, TT], K, T](t: M[K, T])(implicit f: SensMapAtFunctor[M, K, T]) {
    @compileTimeOnly(canOnlyBeUsedInsideModify("at"))
    def at(idx: K): T = sys.error("")

    @compileTimeOnly(canOnlyBeUsedInsideModify("atOrElse"))
    def atOrElse(idx: K, default: => T): T = sys.error("")

    @compileTimeOnly(canOnlyBeUsedInsideModify("index"))
    def index(idx: K): T = sys.error("")

    @compileTimeOnly(canOnlyBeUsedInsideModify("each"))
    def each: T = sys.error("")
  }

  trait SensMapAtFunctor[F[_, _], K, T] {
    def at(fa:       F[K, T], idx: K)(f: T => T): F[K, T]
    def atOrElse(fa: F[K, T], idx: K, default: => T)(f: T => T): F[K, T]
    def index(fa:    F[K, T], idx: K)(f: T => T): F[K, T]
    def each(fa:     F[K, T])(f:   T => T): F[K, T]
  }

  implicit def mapSensFunctor[M[KT, TT] <: Map[KT, TT], K, T](
      implicit cbf: CanBuildFrom[M[K, T], (K, T), M[K, T]]): SensMapAtFunctor[M, K, T] = new SensMapAtFunctor[M, K, T] {
    override def at(fa: M[K, T], key: K)(f: T => T): M[K, T] =
      fa.updated(key, f(fa(key))).asInstanceOf[M[K, T]]
    override def atOrElse(fa: M[K, T], key: K, default: => T)(f: T => T): M[K, T] =
      fa.updated(key, f(fa.getOrElse(key, default))).asInstanceOf[M[K, T]]
    override def index(fa: M[K, T], key: K)(f: T => T): M[K, T] = {
      fa.get(key).map(f).fold(fa)(t => fa.updated(key, t).asInstanceOf[M[K, T]])
    }
    override def each(fa: M[K, T])(f: T => T): M[K, T] = {
      val builder = cbf(fa)
      fa.foreach { case (k, t) => builder += k -> f(t) }
      builder.result
    }
  }

  implicit def seqSensAtFunctor[F[_], T](implicit cbf: CanBuildFrom[F[T], T, F[T]],
                                         ev:           F[T] => SeqLike[T, F[T]]): SensAtFunctor[F, T] = new SensAtFunctor[F, T] {
    override def at(fa: F[T], idx: Int)(f: T => T): F[T] =
      fa.updated(idx, f(fa(idx)))
    override def index(fa: F[T], idx: Int)(f: T => T): F[T] =
      if (idx < fa.size) fa.updated(idx, f(fa(idx))) else fa
  }

  implicit class SensWhen[A](value: A) {
    @compileTimeOnly(canOnlyBeUsedInsideModify("when"))
    def when[B <: A]: B = sys.error("")
  }

  implicit class SensEither[T[_, _], L, R](e: T[L, R])(implicit f: SensEitherFunctor[T, L, R]) {
    @compileTimeOnly(canOnlyBeUsedInsideModify("eachLeft"))
    def eachLeft: L = sys.error("")

    @compileTimeOnly(canOnlyBeUsedInsideModify("eachRight"))
    def eachRight: R = sys.error("")
  }

  trait SensEitherFunctor[T[_, _], L, R] {
    def eachLeft(e:  T[L, R])(f: L => L): T[L, R]
    def eachRight(e: T[L, R])(f: R => R): T[L, R]
  }

  implicit def eitherSensFunctor[T[_, _], L, R]: SensEitherFunctor[Either, L, R] =
    new SensEitherFunctor[Either, L, R] {
      override def eachLeft(e:  Either[L, R])(f: L => L) = e.left.map(f)
      override def eachRight(e: Either[L, R])(f: R => R) = e.right.map(f)
    }
}
