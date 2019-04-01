/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package config

import scala.util.Properties

/**
  * Env
  *
  * @author zhaihao
  * @version 1.0 06/02/2018 16:20
  */
trait Env {
  lazy val env = Properties.propOrNone("env")
}
