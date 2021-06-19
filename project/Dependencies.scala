/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

import sbt._

/** Dependencies
  *
  * @author zhaihao
  * @version 1.0 2019-02-18 13:29
  */
object Dependencies extends AutoPlugin {
  override def requires = empty
  override def trigger  = allRequirements

  object autoImport {
    lazy val scalatest       = "org.scalatest"     %% "scalatest-freespec" % "3.2.7"  % Provided
    lazy val play_json       = "com.typesafe.play" %% "play-json"          % "2.9.2"  % Provided
    lazy val os_lib          = "com.lihaoyi"       %% "os-lib"             % "0.7.4"  % Provided
    lazy val json4s          = "org.json4s"        %% "json4s-jackson"     % "3.6.11" % Provided
    lazy val typesafe_config = "com.typesafe"       % "config"             % "1.4.1"  % Provided
    lazy val squants         = "org.typelevel"     %% "squants"            % "1.7.4"  % Provided
    lazy val argon2          = "de.mkammerer"       % "argon2-jvm"         % "2.9.1"  % Provided

    lazy val java_mail = Seq(
      "javax.mail"   % "javax.mail-api" % "1.6.2" % Provided,
      "com.sun.mail" % "javax.mail"     % "1.6.2" % Provided
    )

    lazy val slick = Seq(
      "com.typesafe.slick"  %% "slick"              % "3.3.3"  % Provided,
      "com.github.tminglei" %% "slick-pg"           % "0.19.6" % Provided,
      "com.github.tminglei" %% "slick-pg_play-json" % "0.19.6" % Provided,
      "com.github.tminglei" %% "slick-pg_jts_lt"    % "0.19.6" % Provided
    )

    lazy val log = Seq(
      "com.typesafe.scala-logging" %% "scala-logging"   % "3.9.4" % Provided,
      "ch.qos.logback"              % "logback-classic" % "1.2.3" % Provided
    )

    val excludes = Seq()

    val overrides = Seq()
  }

}
