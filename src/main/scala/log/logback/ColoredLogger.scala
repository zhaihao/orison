package log.logback

import ch.qos.logback.classic.pattern.{
  Abbreviator,
  ClassNameOnlyAbbreviator,
  ClassicConverter,
  TargetLengthBasedClassNameAbbreviator
}
import ch.qos.logback.classic.spi.{CallerData, ILoggingEvent}
import console.Colors

import scala.Console._

/** ColoredLogger
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2020/6/26
  *   00:43
  */
class ColoredLogger extends ClassicConverter {
  private[this] var abbreviator: Abbreviator = _

  override def start() = {
    val optStr = getFirstOption
    if (optStr != null) try {
      val targetLen = optStr.toInt
      if (targetLen == 0) abbreviator = new ClassNameOnlyAbbreviator()
      else if (targetLen > 0) abbreviator = new TargetLengthBasedClassNameAbbreviator(targetLen)
    } catch {
      case _: NumberFormatException =>
      // FIXME: better error reporting
    }
  }

  override def convert(event: ILoggingEvent) = {
    val fqn    = event.getLoggerName
    val cda    = event.getCallerData
    val line   = if (cda != null && cda.nonEmpty) "(" + cda(0).getLineNumber + ")" else ""
    val logger = if (abbreviator == null) fqn else abbreviator.abbreviate(fqn)
    Colors.text(239, logger + line)
  }
}
