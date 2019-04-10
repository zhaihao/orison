/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package test
import java.io.File

import shell._

import scala.language.postfixOps

/**
  * PreviewLike
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-13 11:19
  */
trait PreviewLike {

  def preview(file: File): Unit = preview(file.getPath)

  /**
    * 没有在windows系统上进行测试
    *
    * @param path file
    * @return
    */
  def preview(path: String): Unit = system.osName.toLowerCase match {
    case name if name.contains("os x")    => s"open $path" !
    case name if name.contains("windows") => s"start /b /max $path" !
    case _                                => throw new Exception(s"Preview function do not support your os: ${system.osName}")
  }

}
