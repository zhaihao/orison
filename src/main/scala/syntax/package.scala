import scala.language.experimental.macros

/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

/**
  * package
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-04-01 11:40
  */
package object syntax extends Syntax {
  def cfor[A](init: A)(test: A => Boolean, next: A => A)(body: A => Unit): Unit = macro CForImpl.cforMacro[A]

  def cfor[A](array: Array[A])(body: A => Unit): Unit = macro CForImpl.cforArrayMacro[A]

  def cfor(r: Range)(body: Int => Unit): Unit = macro CForImpl.cforRangeMacro
}
