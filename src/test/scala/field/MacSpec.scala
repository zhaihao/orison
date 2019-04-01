/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package field

import test.BaseSpec

/**
  * MacSpec
  *
  * @author zhaihao
  * @version 1.0 2017-12-25 10:55
  */
class MacSpec extends BaseSpec {

  "mac 字段处理" in {
    Mac.normalize(Some("44:2a:60:71:cc:82")) ==> Some("44:2a:60:71:cc:82")
    Mac.normalize(Some("44:2A:60:71:CC:82")) ==> Some("44:2a:60:71:cc:82")
    Mac.normalize(None)                      ==> None
    Mac.normalize(Some("44:2A:60:71:CC"))    ==> None
  }

}
