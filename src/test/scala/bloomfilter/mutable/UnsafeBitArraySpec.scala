/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package bloomfilter.mutable

import test.BaseSpec

/** UnsafeBitArraySpec
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2019-08-14
  *   15:36
  */
class UnsafeBitArraySpec extends BaseSpec {
  "test" in {
    val len = 1024 * 1024 * 1024
    val a   = 1024 * 1024
    val bs  = new UnsafeBitArray(len)
    bs.set(a + 1)
    bs.set(a + 2)
    bs.set(a + 3)
    bs.set(a + 4)
    bs.set(a + 5)
    println(s"${bs.get(a + 1)} ${bs.get(a + 2)} ${bs.get(a + 3)} ${bs.get(a + 4)} ${bs.get(a + 5)}")
    bs.fset(a + 3)
    println(s"${bs.get(a + 1)} ${bs.get(a + 2)} ${bs.get(a + 3)} ${bs.get(a + 4)} ${bs.get(a + 5)}")
    bs.dispose()
  }
}
