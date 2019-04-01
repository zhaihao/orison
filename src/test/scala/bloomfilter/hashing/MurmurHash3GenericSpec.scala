/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package bloomfilter.hashing
import test.BaseSpec

/**
  * MurmurHash3GenericSpec
  *
  * @author zhaihao
  * @version 1.0 12/01/2018 13:48
  */
class MurmurHash3GenericSpec extends BaseSpec {

  val values = "scala".getBytes
  "64" in {
    MurmurHash3Generic.murmurhash3_x64_64(values, 0, values.length, 0) ==> 7263519503311918641L
  }

  "128" in {
    MurmurHash3Generic.murmurhash3_x64_128(values, 0, values.length, 0) ==> (-2132731757477280140L, -9050492812920352835L)
  }
}
