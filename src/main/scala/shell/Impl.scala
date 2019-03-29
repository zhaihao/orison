/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package shell
import scala.sys.process.Process

/**
  * Impl
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-29 16:17
  */
class Impl(shell: Seq[String]) {

  def apply(cmd: String) = {
    Process(shell ++ Seq(cmd), None)
  }
}

object Impl {
  val bash = new Impl(Seq("bash", "-c"))
}
