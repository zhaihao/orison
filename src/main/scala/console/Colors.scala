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

  def red(str: String): String = if (isANSISupported) RED + str + RESET else str

  def blue(str: String): String =
    if (isANSISupported) BLUE + str + RESET else str

  def cyan(str: String): String =
    if (isANSISupported) CYAN + str + RESET else str

  def green(str: String): String =
    if (isANSISupported) GREEN + str + RESET else str

  def magenta(str: String): String =
    if (isANSISupported) MAGENTA + str + RESET else str

  def white(str: String): String =
    if (isANSISupported) WHITE + str + RESET else str

  def black(str: String): String =
    if (isANSISupported) BLACK + str + RESET else str

  def yellow(str: String): String =
    if (isANSISupported) YELLOW + str + RESET else str

}
