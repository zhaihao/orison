/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package field

import field.Ip.v4
import test.BaseSpec

/**
  * IpSpec
  *
  * @author zhaihao
  * @version 1.0 27/12/2017 19:22
  */
class IpSpec extends BaseSpec {

  "ipv4" in {
    v4.normalize(Some("127.0.0.1"))     ==> Some("127.0.0.1")
    Ip.v4.normalize(Some("127.0.0.01")) ==> None
    Ip.v4.toInt("127.0.0.1")            ==> 2130706433
    Ip.v4.toString(2130706433)          ==> "127.0.0.1"

    Ip.v4.toInt("128.0.0.1") ==> -2147483647
    Ip.v4.toInt("128.0.0.2") ==> -2147483646

    Ip.v4.toInt("126.0.0.1") ==> 2113929217
    Ip.v4.toInt("126.0.0.2") ==> 2113929218
  }
}
