/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.spec
import test.BaseSpec
import play.api.libs.json.Json

/**
  * MarkSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-22 14:57
  */
class MarkSpec extends BaseSpec {

  import plot._
  "mark test" in {
    Json.json(Mark.Area) ==> """|{
                                |  "type" : "area"
                                |}""".stripMargin
  }
}
