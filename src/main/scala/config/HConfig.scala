/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package config

import com.typesafe.config.{Config, ConfigFactory}

import scala.util.Properties
import syntax.config._

/**
  * TypesafeConfig
  *
  * @author zhaihao
  * @version 1.0 06/02/2018 17:00
  */
trait HConfig {
  lazy val config: Config = {
    val file = Properties.propOrElse("config.resource", "application.conf")

    ConfigFactory.load(file)
  }

  lazy val env = config.getOrElse("env", "dev")
}
