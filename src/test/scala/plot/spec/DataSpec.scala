/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.spec

import test.BaseSpec
import play.api.libs.json.Json
import plot._
/**
  * DataSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-21 16:56
  */
class DataSpec extends BaseSpec {
  "data json" in {
    Json.json(Data("1")) ==> """|{
                                |  "url" : "1"
                                |}""".stripMargin
  }
}
