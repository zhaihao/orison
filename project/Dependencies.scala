/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

import sbt._

/**
  * Dependencies
  *
  * @author zhaihao
  * @version 1.0 2019-02-18 13:29
  */
object Dependencies extends AutoPlugin {
  override def requires = empty
  override def trigger  = allRequirements

  object autoImport {
    lazy val scalatest       = "org.scalatest"     %% "scalatest" % "3.0.7" % Provided
    lazy val typesafe_config = "com.typesafe"      % "config"     % "1.3.3" % Provided
    lazy val play_json       = "com.typesafe.play" %% "play-json" % "2.6.9" % Provided

    lazy val log = Seq(
      "com.typesafe.scala-logging" %% "scala-logging"  % "3.9.2" % Provided,
      "ch.qos.logback"             % "logback-classic" % "1.2.3" % Provided
    )

    val excludes = Seq()

    val overrides = Seq()
  }

}
