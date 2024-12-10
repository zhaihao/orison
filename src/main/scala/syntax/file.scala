/*
 * Copyright (c) 2020-2024.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package syntax

import java.io.File

/**
  * file
  *
  * @author zhaihao
  * @version 1.0
  * @since 2024-12-10 13:38
  */
object file {
  extension (f: File) {
    def deleteRecursively: Unit = {
      if (f.isDirectory)
        f.listFiles().foreach(fi => fi.deleteRecursively)
      if (f.exists && !f.delete)
        throw new Exception(s"Unable to delete ${f.getAbsolutePath}")
    }
  }
}
