/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package json

import java.sql.Timestamp

import play.api.libs.json.{JsNumber, JsString, Json}
import test.BaseSpec

/** FormatterSpec
  *
  * @author
  *   zhaihao
  * @version 1.0
  * 2018/4/3 14:42
  */
class FormatterSpec extends BaseSpec {

  "timestamp to json" - {
    val ts = 1522737854671L

    "JsNumber" in {
      Json.toJson(new Timestamp(ts)) ==> JsNumber(1522737854671L)
    }

    "JsString" in {
      import Formatter._
      Json.toJson(new Timestamp(ts)) ==> JsString("2018-04-03 14:44:14.671")
    }

    "json to timestamp" - {
      val json = Json.parse(""" {"ts": 1522737854671} """)

      "play json lib doesn't have Reads for Timestamp" in {
        assertDoesNotCompile("""(json \ "ts").as[Timestamp]""")
      }

      "provider reads" in {
        import Formatter._
        (json \ "ts").as[Timestamp] ==> new Timestamp(ts)
        (Json.parse(""" {"ts": "2018-04-03 14:44:14.671"} """) \ "ts")
          .as[Timestamp] ==> new Timestamp(ts)
      }
    }
  }

  "map format" in {
    import Formatter._
    val values = Seq(
      Map("a" -> "C", "b" -> 2),
      Map("a" -> "C", "b" -> 7),
      Map("a" -> "C", "b" -> 4),
      Map("a" -> "D", "b" -> 1),
      Map("a" -> "D", "b" -> 2),
      Map("a" -> "D", "b" -> 6),
      Map("a" -> "E", "b" -> 8),
      Map("a" -> "E", "b" -> 4),
      Map("a" -> "E", "b" -> 7)
    )
    println(Json.toJson(values))
  }
}
