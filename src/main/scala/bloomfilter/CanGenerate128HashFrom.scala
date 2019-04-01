/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package bloomfilter
import hashing.MurmurHash3Generic
import unsafe._

trait CanGenerate128HashFrom[From] {
  def generateHash(from: From): (Long, Long)
}

object CanGenerate128HashFrom {

  implicit case object CanGenerate128HashFromByteArray extends CanGenerate128HashFrom[Array[Byte]] {
    override def generateHash(from: Array[Byte]): (Long, Long) =
      MurmurHash3Generic.murmurhash3_x64_128(from, 0, from.length, 0)
  }

  implicit case object CanGenerate128HashFromString extends CanGenerate128HashFrom[String] {

    private val valueOffset =
      unsafe.objectFieldOffset(classOf[String].getDeclaredField("value"))

    override def generateHash(from: String): (Long, Long) = {
      val value = unsafe.getObject(from, valueOffset).asInstanceOf[Array[Char]]
      MurmurHash3Generic.murmurhash3_x64_128(value, 0, from.length * 2, 0)
    }
  }

  implicit case object CanGenerate128HashFromLong extends CanGenerate128HashFrom[Long] {
    override def generateHash(from: Long): (Long, Long) = {
      val hash = MurmurHash3Generic.fMix64(from)
      (hash, hash)
    }
  }

}
