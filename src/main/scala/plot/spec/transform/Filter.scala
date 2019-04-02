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
  * Filter
  *
  *  只支持 表达式
  *  表达式写法参考 [[https://vega.github.io/vega/docs/expressions/]]
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-26 17:37
  */
case class Filter(filter: String) extends Transform

object Filter {
  implicit val FilterFormat = Json.format[Filter]
}
