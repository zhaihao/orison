/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package io
import java.io.{File, FileOutputStream}
import java.net.URL
import java.nio.channels.Channels

/** CanDownloadFile
  *
  * @author
  *   zhaihao
  * @version 1.0
  * 2018-12-06 15:49
  */
class CanDownloadFile(localPath: String, networkPath: String) extends File(localPath) {

  // auto download
  if (!exists()) download

  // 适合下载小文件
  private def download = {
    val channel = Channels.newChannel(new URL(networkPath).openStream())
    this.getParentFile.mkdirs()
    val fileOutputStream = new FileOutputStream(new File(localPath))
    fileOutputStream.getChannel.transferFrom(channel, 0, Long.MaxValue)
  }
}

object CanDownloadFile {

  def apply(localPath: String, networkPath: String): CanDownloadFile =
    new CanDownloadFile(localPath, networkPath)
}
