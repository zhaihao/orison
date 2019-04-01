/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package field
import syntax.string._

/**
  * Imei
  *
  * @author zhaihao
  * @version 1.0 2017-12-27 19:08
  */
object Imei {

  //language=RegExp
  val REGEX =
    """[0-9A-Fa-f]{14,15}"""

  def toFifteen(imei: String) =
    if (imei != null && imei.matches(REGEX)) {
      if (imei.length == 15) Some(imei.toLowerCase)
      else {
        var sum = 0
        for (i <- 0 to 13) {
          val c = imei(i).toString.toInt(16)
          val v = if (i % 2 == 0) c else { val a = c * 2; a % 10 + a / 10 }
          sum += v
        }
        val mask = if (sum % 10 == 0) 0 else 10 - sum % 10
        Some(imei.toLowerCase + mask)
      }
    } else None

  def toFourteen(imei: String) =
    if (imei != null && imei.matches(REGEX)) {
      Some(
        if (imei.length == 15) imei.substring(0, 14).toLowerCase
        else imei.toLowerCase)
    } else None

  // 小写 15 位
  def normalize(imei: Option[String]) = imei.flatMap(toFifteen)
}
