/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package test

import org.scalatest.Assertions._

/**
  * ArrowAssertLike
  *
  * @author zhaihao
  * @version 1.0 2017/12/8 11:22
  */
trait ArrowAssertLike {

  implicit class ArrowAssertHold(lhs: Any) {

    def ==>[V](rhs: V) = assertResult(rhs)(lhs)

    def !=>[V](rhs: V) = assert(lhs != rhs)

    // for async future.map(_ >>> ())
    def >>>[V](rhs: V) = assertResult(1)(1)
  }

}
