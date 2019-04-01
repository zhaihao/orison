/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package syntax

import java.io.File

import scala.language.implicitConversions

/**
  * FileOps
  *
  * @author zhaihao
  * @version 1.0 2018-01-18 16:42
  */
final class FileOps private[syntax] (private val file: File) extends AnyVal {
  def deleteRecursively: Unit = {
    if (file.isDirectory)
      file.listFiles().foreach(f => new FileOps(f).deleteRecursively)
    if (file.exists && !file.delete)
      throw new Exception(s"Unable to delete ${file.getAbsolutePath}")
  }
}

trait ToFileOps {
  @inline implicit def toFileOps(file: File): FileOps = new FileOps(file)
}
