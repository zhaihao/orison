import play.api.libs.json.{Json, Writes}
import test.PreviewLike

import scala.language.implicitConversions
/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

/** package
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2019-03-22
  *   11:32
  */
package object plot {

  val VEGA_VERSION      = "5.17.0"
  val VEGA_LITE_VERSION = "4.17.0"
  val VEGA_EMBED        = "6.12.2"
  val schema            = s"https://vega.github.io/schema/vega-lite/v4.json"
  // 入口
  def vega = Vega()

  type Theme = String

  implicit class BrowseAble(render: HtmlRenderer) extends PreviewLike {

    def browse(foreground: Boolean = true) = {
      val tmp = os.temp(suffix = ".html", deleteOnExit = false)
      os.write.over(tmp, render.page)
      preview(tmp.toIO, foreground)
    }
  }
}
