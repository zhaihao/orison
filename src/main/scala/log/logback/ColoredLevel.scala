/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package log.logback

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.pattern.ClassicConverter
import ch.qos.logback.classic.spi.ILoggingEvent
import console.Colors

/**
  * ColoredLevel
  * {{{
  *   %coloredLevel %logger{15} - %message%n%xException{5}
  * }}}
  *
  * @author zhaihao
  * @version 1.0 2018-03-21 16:32
  */
class ColoredLevel extends ClassicConverter {

  def convert(event: ILoggingEvent): String = {
    event.getLevel match {
      case Level.TRACE => "[" + Colors.text(28, "trace") + "]"
      case Level.DEBUG => "[" + Colors.text(51, "debug") + "]"
      case Level.INFO  => "[" + Colors.text(82, "info") + " ]"
      case Level.WARN  => "[" + Colors.text(11, "warn") + " ]"
      case Level.ERROR => "[" + Colors.text(196, "error") + "]"
    }
  }

}
