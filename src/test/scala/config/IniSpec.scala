/*
 * Copyright (c) 2020-2023.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package config

import com.typesafe.scalalogging.StrictLogging
import test.BaseSpec

/**
  * IniSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2023/1/18 19:49
  */
class IniSpec extends BaseSpec with StrictLogging {

  "parse path" in {
    val ini = Ini.parse(os.resource / "test.ini")
    ini.get("a") ==> "1"
    ini.get("b") ==> "2"

    ini.get("x", "xy") ==> "1"
    ini.get("y", "xy") ==> "2"
  }
}
