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

  require(sys.props("os.name").toLowerCase.contains("os x"), "预览文件功能只支持 os x 系统")

  def preview(file: File) = s"open ${file.getPath}" !

  def preview(path: String) = s"open $path" !
}
