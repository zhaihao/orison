import shell.Impl._

import scala.language.implicitConversions
import scala.sys.process.ProcessBuilder
/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

/** package
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2019-03-29
  *   16:16
  */
package object shell {
  implicit def string2BashProcess(cmd: String): ProcessBuilder = bash(cmd)
}
