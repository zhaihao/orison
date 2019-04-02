/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.spec.transform
import play.api.libs.json.Json
import plot.spec.Transform

/**
  * Calculate
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-26 14:46
  */
case class Calculate(calculate: String, as: String) extends Transform

object Calculate {
  implicit val CalculateFormat = Json.format[Calculate]
}
