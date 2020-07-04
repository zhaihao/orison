/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package console

/**
  * Colors
  *
  * @author zhaihao
  * @version 1.0 2018-03-21 16:35
  */
object Colors {

  import scala.Console._

  lazy val isANSISupported = {
    sys.props
      .get("sbt.log.noformat")
      .map(_ != "true")
      .orElse {
        sys.props
          .get("os.name")
          .map(_.toLowerCase(java.util.Locale.ENGLISH))
          .filter(_.contains("windows"))
          .map(_ => false)
      }
      .getOrElse(true)
  }

  def red(str:     String): String = if (isANSISupported) RED + str + RESET else str
  def blue(str:    String): String = if (isANSISupported) BLUE + str + RESET else str
  def cyan(str:    String): String = if (isANSISupported) CYAN + str + RESET else str
  def green(str:   String): String = if (isANSISupported) GREEN + str + RESET else str
  def magenta(str: String): String = if (isANSISupported) MAGENTA + str + RESET else str
  def white(str:   String): String = if (isANSISupported) WHITE + str + RESET else str
  def black(str:   String): String = if (isANSISupported) BLACK + str + RESET else str
  def yellow(str:  String): String = if (isANSISupported) YELLOW + str + RESET else str

  def RB_RED(str:    String): String = if (isANSISupported) "\u001b[38;5;196m" + str + RESET else str
  def RB_ORANGE(str: String): String = if (isANSISupported) "\u001b[38;5;202m" + str + RESET else str
  def RB_YELLOW(str: String): String = if (isANSISupported) "\u001b[38;5;226m" + str + RESET else str
  def RB_GREEN(str:  String): String = if (isANSISupported) "\u001b[38;5;082m" + str + RESET else str
  def RB_BLUE(str:   String): String = if (isANSISupported) "\u001b[38;5;021m" + str + RESET else str
  def RB_INDIGO(str: String): String = if (isANSISupported) "\u001b[38;5;093m" + str + RESET else str
  def RB_VIOLET(str: String): String = if (isANSISupported) "\u001b[38;5;163m" + str + RESET else str

  def render(colorExpr: String, str: String) = if (isANSISupported) colorExpr + str + RESET else str

}
