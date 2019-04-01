/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package bloomfilter.mutable
import test.BaseSpec

/**
  * BloomFilter
  *
  * @author zhaihao
  * @version 1.0 12/01/2018 12:00
  */
class BloomFilterSpec extends BaseSpec {
  "bloom filter" in {
    val expectedElements  = 1000000
    val falsePositiveRate = 0.1
    val bf                = BloomFilter[String](expectedElements, falsePositiveRate)

    bf.add("scala")
    bf.add("java")
    bf.mightContain("scala") ==> true
    bf.mightContain("hello") ==> false

    bf.dispose()
  }

  "bloom filter 128" in {
    val expectedElements  = 1000000
    val falsePositiveRate = 0.1

    val bf = _128bit.BloomFilter[String](expectedElements, falsePositiveRate)
    bf.add("scala")
    bf.add("java")
    bf.mightContain("scala") ==> true
    bf.mightContain("hello") ==> false

    bf.dispose()
  }
}
