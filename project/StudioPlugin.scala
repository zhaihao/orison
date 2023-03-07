/*
 * Copyright (c) 2020-2023.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

import sbt.Keys._
import sbt._

import scala.Console.{RESET, YELLOW, MAGENTA}

/**
  * Studio
  *
  * @author zhaihao
  * @version 1.0
  * @since 2023/3/7 22:09
  */
object StudioPlugin extends AutoPlugin {
  override def requires = empty
  override def trigger  = allRequirements

  object autoImport {
    val studioTarget = settingKey[File]("mac studio output dir")
    val isStudio = java.net.InetAddress.getLocalHost.getHostName.contains("Studio")
  }

  import autoImport._

  override val projectSettings: Seq[Def.Setting[_]] = Seq(
    studioTarget := {
      if (isStudio) file(s"/Volumes/RamDisk/IDEA/${name.value}")
      else baseDirectory.value / "target" // 无法使用 target.value 会造成循环依赖
    },

    // give a feed back
    onLoadMessage := {
      if(isStudio) s"""${YELLOW}Running on Studio, output will be set to $MAGENTA${studioTarget.value}$RESET.""".stripMargin
      else ""
    }
  )
}
