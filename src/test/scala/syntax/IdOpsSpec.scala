/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package syntax
import test.BaseSpec

/** IdOpsSpec
  *
  * @author
  *   zhaihao
  * @version 1.0
  * 2018-01-18 16:16
  */
class IdOpsSpec extends BaseSpec {

  import syntax.id._

  "|>" in {
    "abc" |> println
  }

  "some" in {
    1.some ==> Some(1)
    assertDoesNotCompile("null.some")
  }
}
