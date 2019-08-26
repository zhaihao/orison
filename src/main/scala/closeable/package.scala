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
  * @since 2019/8/26 4:07 下午
  */
package object closeable {

  def using[T <: AutoCloseable, B](c: T)(f: T => B): B = {
    try {
      f(c)
    } finally {
      if (c != null) c.close()
    }
  }
}
