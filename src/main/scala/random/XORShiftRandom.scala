/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package random

import java.nio.ByteBuffer

import scala.util.hashing.MurmurHash3

/** XORShiftRandom
  *
  * copy from spark framework
  *
  * This class implements a XORShift random number generator algorithm Source: Marsaglia, G. (2003). Xorshift RNGs.
  * Journal of Statistical Software, Vol. 8, Issue 14. [[http://www.jstatsoft.org/v08/i14/paper]] This implementation is
  * approximately 3.5 times faster than java.util.Random, partly because of the algorithm, but also due to renouncing
  * thread safety. JDK's implementation uses an AtomicLong seed, this class uses a regular Long. We can forgo thread
  * safety since we use a new instance of the RNG for each thread.
  *
  * @author
  *   zhaihao
  * @version 1.0
  * 23/01/2018 20:08
  */
class XORShiftRandom(init: Long) extends java.util.Random(init) {

  def this() = this(System.nanoTime)

  private var seed = XORShiftRandom.hashSeed(init)

  override protected def next(bits: Int): Int = {
    var nextSeed = seed ^ (seed << 21)
    nextSeed ^= (nextSeed >>> 35)
    nextSeed ^= (nextSeed << 4)
    seed = nextSeed
    (nextSeed & ((1L << bits) - 1)).asInstanceOf[Int]
  }

  override def setSeed(s: Long) = {
    seed = XORShiftRandom.hashSeed(s)
  }

}

object XORShiftRandom {

  def hashSeed(seed: Long): Long = {
    val bytes    = ByteBuffer.allocate(java.lang.Long.SIZE).putLong(seed).array()
    val lowBits  = MurmurHash3.bytesHash(bytes)
    val highBits = MurmurHash3.bytesHash(bytes, lowBits)
    (highBits.toLong << 32) | (lowBits.toLong & 0xffffffffL)
  }
}
