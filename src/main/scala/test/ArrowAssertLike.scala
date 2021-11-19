/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package test

import org.scalactic.{Prettifier, TripleEquals, source}
import org.scalatest.Assertions._
import org.scalatest.{Failed, Succeeded}

/** ArrowAssertLike
  *
  * @author
  *   zhaihao
  * @version 1.0
  * 2017-12-8 11:22
  */
trait ArrowAssertLike extends TripleEquals {

  implicit class ArrowAssertHold(lhs: Any) {

    def ==>[V](rhs: V)(implicit pf: Prettifier, pos: source.Position) = assertResult(rhs)(lhs)(pf, pos)

    def !=>[V](rhs: V)(implicit pf: Prettifier, pos: source.Position) = assert(lhs != rhs)(pf, pos)

    // fast pass test
    // for async future.map(_ >>> ())
    def >>> = Succeeded

    // fast fail test
    // for async future.map(_ !>> ())
    def !>>(implicit pos: source.Position) = Failed.apply()(pos)
  }

}
