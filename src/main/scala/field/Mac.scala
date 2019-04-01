/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package field

/**
  * Mac
  *
  * @author zhaihao
  * @version 1.0 2017-12-23 16:09
  */
object Mac {
  // language=RegExp
  val REGEX = "([A-Fa-f0-9]{2}:){5}[A-Fa-f0-9]{2}"

  // 默认小写 44:2a:60:71:cc:82
  def normalize(mac: Option[String]) =
    mac.flatMap(m => if (m.matches(REGEX)) mac else None).map(_.toLowerCase)
}
