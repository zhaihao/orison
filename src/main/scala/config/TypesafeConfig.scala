/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package config

import com.typesafe.config.{Config, ConfigFactory}

import scala.util.Properties

/**
  * TypesafeConfig
  *
  * @author zhaihao
  * @version 1.0 06/02/2018 17:00
  */
trait TypesafeConfig {
  val config: Config
}

trait StandardTypesafeConfig extends TypesafeConfig with Env {
  lazy val config: Config = {
    val file = Properties.propOrElse("config.resource", "application.conf")
    env.foreach(e => Properties.setProp("config.resource", s"$e/$file"))

    ConfigFactory.defaultApplication()
  }
}
