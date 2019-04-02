/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.spec.encoding
import play.api.libs.json.Json
import plot._

/**
  * Bin
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-26 17:10
  */
case class Bin(binned: Option[Boolean] = None, step: Option[Int] = None)

object Bin {
  implicit val BinFormat = Json.format[Bin]

  // 这个参数是 binned，如果 设置 bin = true，则是 设置 binned 为false
  def apply(): Bin = new Bin(false)
}
