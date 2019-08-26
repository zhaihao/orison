/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.dsl
import json.Formatter._
import play.api.libs.json._

/**
  * DataDSL
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-22 10:44
  */
trait DataDSL {
  protected var data: JsValue = JsNull

  def withUrl(url: String): this.type = {
    data = JsObject(Map("url" -> JsString(url)))

    this
  }

  def withValues(values: Seq[Map[String, Any]]): this.type = {
    data = JsObject(Map("values" -> Json.toJson(values)))
    this
  }

  def withClasses[T <: Product](values: Seq[T])(implicit ev: Writes[T]): this.type = {
    data = JsObject(Map("values" -> Json.toJson(values)))
    this
  }
}
