import play.api.libs.json.{Json, Writes}
import test.PreviewLike

import scala.language.implicitConversions
/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

/**
  * package
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-22 11:32
  */
package object plot {

  /**
    * @see [[https://vega.github.io/vega-lite/usage/embed.html#cdn]]
    */
  val VEGA_VERSION      = "5.3.1"
  val VEGA_LITE_VERSION = "3.0.0"
  val VEGA_EMBED        = "4.0.0-rc1"
  val SCHEMA_VERSION    = "v3"
  val schema            = s"https://vega.github.io/schema/vega-lite/$SCHEMA_VERSION.json"
  // 入口
  def vega(width: Option[Int] = None, height: Option[Int] = None) = Vega(width, height)
  def vega = Vega()

  //type alias
  type Theme       = String
  type FieldType   = String
  type AggOp       = String
  type TimeUnit    = String
  type TitleAnchor = String
  type BaseLine    = String
  type Orient      = String

  // make all implicit in plot._
  implicit def antToOption[T](t: T) = option.anyToOption(t)

  object PJson {

    def json[T](o: T)(implicit tjs: Writes[T]) = {
      Json.prettyPrint(Json.toJson(o))
    }
  }

  implicit def json2PJson(json: Json.type) = PJson

  implicit class BrowseAble(render: HtmlRenderer) extends PreviewLike {

    def browse = {
      val tmp = os.temp(suffix = ".html", deleteOnExit = false)
      os.write.over(tmp, render.page)
      preview(tmp.toIO)
    }
  }
  // implicit end
}
