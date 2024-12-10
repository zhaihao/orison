/*
 * Copyright (c) 2020-2024.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package syntax

/**
  * id
  *
  * @author zhaihao
  * @version 1.0
  * @since 2024-12-10 14:26
  */
object id {
  extension [A](self: A) {
    def |>[B](f: A => B): B = f(self)

    def some: Some[A] = Some(self)
  }
}
