/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package bloomfilter.mutable._128bit

import java.io.{DataInputStream, DataOutputStream, InputStream, OutputStream}

import bloomfilter.mutable
import bloomfilter.mutable.UnsafeBitArray

@SerialVersionUID(1L)
class BloomFilter[T] private (val numberOfBits: Long, val numberOfHashes: Int, private val bits: UnsafeBitArray)(implicit
    canGenerateHash: bloomfilter.CanGenerate128HashFrom[T]
) extends Serializable {

  def this(numberOfBits: Long, numberOfHashes: Int)(implicit canGenerateHash: bloomfilter.CanGenerate128HashFrom[T]) = {
    this(numberOfBits, numberOfHashes, new mutable.UnsafeBitArray(numberOfBits))
  }

  def add(x: T): Unit = {
    val hash = canGenerateHash.generateHash(x)

    var i = 0
    while (i < numberOfHashes) {
      val computedHash = hash._1 + i * hash._2
      bits.set((computedHash & Long.MaxValue) % numberOfBits)
      i += 1
    }
  }

  def mightContain(x: T): Boolean = {
    val hash = canGenerateHash.generateHash(x)

    var i = 0
    while (i < numberOfHashes) {
      val computedHash = hash._1 + i * hash._2
      if (!bits.get((computedHash & Long.MaxValue) % numberOfBits))
        return false
      i += 1
    }
    true
  }

  def expectedFalsePositiveRate(): Double = {
    math.pow(bits.getBitCount.toDouble / numberOfBits, numberOfHashes.toDouble)
  }

  def writeTo(out: OutputStream): Unit = {
    val dout = new DataOutputStream(out)
    dout.writeLong(numberOfBits)
    dout.writeInt(numberOfHashes)
    bits.writeTo(out)
  }

  def dispose(): Unit = bits.dispose()

}

object BloomFilter {

  def apply[T](numberOfItems: Long, falsePositiveRate: Double)(implicit
      canGenerateHash: bloomfilter.CanGenerate128HashFrom[T]
  ): BloomFilter[T] = {

    val nb = optimalNumberOfBits(numberOfItems, falsePositiveRate)
    val nh = optimalNumberOfHashes(numberOfItems, nb)
    new BloomFilter[T](nb, nh)
  }

  def optimalNumberOfBits(numberOfItems: Long, falsePositiveRate: Double): Long = {
    math
      .ceil(
        -1 * numberOfItems * math.log(falsePositiveRate) / math.log(2) / math
          .log(2)
      )
      .toLong
  }

  def optimalNumberOfHashes(numberOfItems: Long, numberOfBits: Long): Int = {
    math.ceil(numberOfBits / numberOfItems * math.log(2)).toInt
  }

  def readFrom[T](in: InputStream)(implicit canGenerateHash: bloomfilter.CanGenerate128HashFrom[T]): BloomFilter[T] = {
    val din            = new DataInputStream(in)
    val numberOfBits   = din.readLong()
    val numberOfHashes = din.readInt()
    val bits           = new mutable.UnsafeBitArray(numberOfBits)
    bits.readFrom(in)
    new BloomFilter[T](numberOfBits, numberOfHashes, bits)
  }

}
