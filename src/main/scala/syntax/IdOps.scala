/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package syntax

import scala.language.implicitConversions

/**
  * IdOps
  *
  * @author zhaihao
  * @version 1.0 2018-01-18 16:13
  */
final class IdOps[A] private[syntax] (private val self: A) extends AnyVal {
  def |>[B](f: A => B): B = f(self)

  def some: Some[A] = Some(self)
}

trait ToIdOps {
  @inline implicit def ToIdOps[A](a: A): IdOps[A] = new IdOps[A](a)
}
