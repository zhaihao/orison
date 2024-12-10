/*
 * Copyright (c) 2020-2024.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package maths

/**
  * power
  *
  * @author zhaihao
  * @version 1.0
  * @since 2024-12-10 14:13
  */
object power {
  extension [T](a: T)(using d: Numeric[T]) {
    def ^*(b: Double): Double = math.pow(d.toDouble(a), b)
  }
}
