/*
 * Copyright (c) 2020.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package log.logback

import ch.qos.logback.classic.pattern.ClassicConverter
import ch.qos.logback.classic.spi.ILoggingEvent
import scala.Console._

/**
  * ColoredAkkaSource
  *
  * @author zhaihao
  * @version 1.0
  * @since 2020/7/27 23:32
  */
class ColoredAkkaSource extends ClassicConverter {
  override def convert(event: ILoggingEvent) = {
    val str = event.getMDCPropertyMap.get("akkaSource")
    if (str == null) ""
    else " " + WHITE + "[" + str + "]" + RESET
  }
}
