/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package field

import test.BaseSpec

/** ImeiSpec
  *
  * @author
  *   zhaihao
  * @version 1.0
  * 2017-12-27 19:09
  */
class ImeiSpec extends BaseSpec {

  "imei 字段处理" in {
    Imei.normalize(Some("a1000027741ac4")) ==> Some("a1000027741ac43")
    Imei.normalize(Some("A1000027741AC4")) ==> Some("a1000027741ac43")
    Imei.normalize(Some("A1000027741AC"))  ==> None
    Imei.normalize(None)                   ==> None

    Imei.toFifteen("A1000027741AC4")  ==> Some("a1000027741ac43")
    Imei.toFifteen("A1000027741AC43") ==> Some("a1000027741ac43")

    Imei.toFourteen("A1000027741AC4")  ==> Some("a1000027741ac4")
    Imei.toFourteen("A1000027741AC43") ==> Some("a1000027741ac4")
  }
}
