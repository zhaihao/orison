/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.spec.encoding

import play.api.libs.json.Json

/**
  * Axis
  *
  * @author zhaihao
  * @version 1.0 2019-03-24 17:13
  */
case class Axis(title:           Option[String] = None,
                titleAlign:      Option[String] = None,
                titleAngle:      Option[Double] = None,
                titleBaseline:   Option[String] = None,
                titleColor:      Option[String] = None,
                titleFont:       Option[String] = None,
                titleFontSize:   Option[Int]    = None,
                titleFontWeight: Option[Int]    = None,
                titleLimit:      Option[Int]    = None,
                titleOpacity:    Option[Double] = None)

object Axis {
  implicit val AxisFormat = Json.format[Axis]
}
