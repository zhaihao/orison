/*
 * Copyright (c) 2021.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package syntax

import test.BaseSpec

/** CForSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2021/4/28 18:21
  */
class CForSpec extends BaseSpec {

  "cfor" in {
    var i = 0
    cfor(1 to 10) { i += _ }
    i ==> 55
  }
}
