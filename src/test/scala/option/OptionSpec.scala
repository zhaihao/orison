/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package option
import test.BaseSpec

/** OptionSpec
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2019-03-21
  *   17:19
  */
class OptionSpec extends BaseSpec {
  def test1(a: Option[Int])    = a.get * 2
  def test2(b: Option[String]) = b.get.reverse

  "any to option" in {
    test1(2)     ==> 4
    test2("abc") ==> "cba"
  }
}
