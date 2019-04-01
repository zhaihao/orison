/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package field

import java.time.LocalDate

import test.BaseSpec

/**
  * PeopleIDSpec
  *
  * @author zhaihao
  * @version 1.0 2018/3/31 17:55
  */
class PeopleIDSpec extends BaseSpec {

  val pid = "110105196608238015"

  import PeopleID._

  "test age" in {
    age(pid) ==> LocalDate.now().getYear - 1966
  }

  "test sex" in {
    sex(pid) ==> 1
  }

  "test city" in {
    city(pid) ==> 110105
  }
}
