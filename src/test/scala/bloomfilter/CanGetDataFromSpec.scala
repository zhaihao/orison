/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package bloomfilter
import bloomfilter.CanGetDataFrom.CanGetDataFromArrayChar
import test.BaseSpec

/**
  * CanGetDataFromSpec
  *
  * @author zhaihao
  * @version 1.0 12/01/2018 15:59
  */
class CanGetDataFromSpec extends BaseSpec {

  "CanGetDataFromArrayChar" in {
    CanGetDataFromArrayChar.getByte(Array[Char]('a'), 0) ==> 97.toByte
    CanGetDataFromArrayChar.getByte(Array[Char]('a'), 1) ==> 0.toByte

    CanGetDataFromArrayChar.getByte(Array[Char]('a', 'b'), 0) ==> 97.toByte
    CanGetDataFromArrayChar.getByte(Array[Char]('a', 'b'), 1) ==> 0.toByte
    CanGetDataFromArrayChar.getByte(Array[Char]('a', 'b'), 2) ==> 98.toByte
    CanGetDataFromArrayChar.getByte(Array[Char]('a', 'b'), 3) ==> 0.toByte

    CanGetDataFromArrayChar.getLong(Array[Char]('a', 'b', 'c', 'd'), 0) ==> (
      (0.toLong << 56) |
        (('d'.toByte & 0xffL) << 48) |
        ((0 & 0xffL) << 40) |
        (('c'.toByte & 0xffL) << 32) |
        ((0 & 0xffL) << 24) |
        (('b' & 0xffL) << 16) |
        ((0 & 0xffL) << 8) |
        'a' & 0xffL
    )

  }
}
