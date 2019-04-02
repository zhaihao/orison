/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.dsl
import play.api.libs.json.{JsObject, JsString, Json}
import plot._
import plot.spec.Data

/**
  * JsonDSL
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-27 17:58
  */
trait JsonDSL {
  var json: Option[String] = None

  def json(json: String, data: Data): this.type = {
    this.json = Json.prettyPrint(
      Json.parse(json).as[JsObject]
        + ("data", Json.toJson(data))
    )

    this
  }

}
