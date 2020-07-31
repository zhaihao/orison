/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package json

import com.typesafe.scalalogging.StrictLogging
import play.api.libs.json.{JsResultException, Json}
import test.BaseSpec

/**
  * UnionSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-07-10 13:22
  */
class UnionSpec extends BaseSpec with StrictLogging {

  sealed trait UnionType

  case class INT(value: Int) extends UnionType

  object INT {
    implicit val format = Json.format[INT]
  }

  case class STRING(value: String) extends UnionType

  object STRING {
    implicit val format = Json.format[STRING]
  }

  implicit val format = Union
    .from[UnionType]("valueType")
    .and[INT]("int")
    .and[STRING]("string")
    .format

  "to json" in {
    //language=JSON
    Json.toJson(INT(10)).toString() ==> """{"valueType":"int","value":10}"""
    //language=JSON
    Json.toJson(STRING("10")).toString() ==> """{"valueType":"string","value":"10"}"""
  }

  "parse object" in {
    //language=JSON
    Json.parse("""{"value":10,"valueType":"int"}""").as[INT] ==> INT(10)
    //language=JSON
    Json.parse("""{"value":"10","valueType":"string"}""").as[STRING] ==> STRING("10")

    intercept[JsResultException] {
      //language=JSON
      Json.parse("""{"value":"10","valueType":"string"}""").as[Int]
    }
  }
}
