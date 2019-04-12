/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.dsl
import play.api.libs.json.{JsNumber, JsObject, JsValue, Json}
import plot._

/**
  * JsonDSL
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-27 17:58
  */
trait JsonDSL { vega: Vega =>
  var json: Option[String] = None

  def json(json: String): this.type = {
    this.json = Json.prettyPrint(
      Json.parse(json).as[JsObject]
        ++ JsObject(
          Seq.empty[(String, JsValue)] ++
            vega.width.map("width"   -> JsNumber(_)) ++
            vega.height.map("height" -> JsNumber(_)) ++
            vega.data.map("data"     -> Json.toJson(_)))
    )

    this
  }

}
