/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package syntax

import java.io.File

import test.BaseSpec

/**
  * FileOpsSpec
  *
  * @author zhaihao
  * @version 1.0 2017-12-27 18:57
  */
class FileOpsSpec extends BaseSpec {
  import syntax.file._

  "deleteRecursively" in {
    val file = new File("/tmp/a/b/c")
    file.mkdirs() ==> true
    file.exists() ==> true
    val f = new File("/tmp/a")
    f.deleteRecursively
    f.exists() ==> false
  }
}
