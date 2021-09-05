package hash

import java.lang.Integer.{rotateLeft => rotl32}

/** MurmurHash3_32
  *
  * See [[https://github.com/aappleby/smhasher]]
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2021/3/30
  *   3:56 下午
  */
object MurmurHash3_32 extends FastHash[Int] {
  val C1 = 0xcc9e2d51
  val C2 = 0x1b873593

  final def hashByte(input: Byte, seed: Int): Int =
    avalanche(fMix(seed, input & 0xff) ^ 1)

  final def hashInt(input: Int, seed: Int): Int =
    avalanche(mix(seed, input) ^ 4)

  final def hashLong(input: Long, seed: Int): Int =
    avalanche(mix(mix(seed, input.asInstanceOf[Int]), (input >> 32).asInstanceOf[Int]) ^ 8)

  private[hash] final def fMix(hash: Int, k: Int): Int =
    hash ^ rotl32(k * C1, 15) * C2

  private[hash] final def mix(hash: Int, k: Int): Int =
    rotl32(fMix(hash, k), 13) * 5 + 0xe6546b64

  private[hash] final def avalanche(hash: Int): Int = {
    val k1 = (hash ^ (hash >>> 16)) * 0x85ebca6b
    val k2 = (k1 ^ (k1 >>> 13)) * 0xc2b2ae35
    k2 ^ (k2 >>> 16)
  }

  private[hash] final def hashBytes(input: Array[Byte], offset: Long, length: Int, seed: Int): Int = {
    var hash        = seed
    var off         = offset
    var unprocessed = length

    while (unprocessed >= 4) {
      hash = mix(hash, unsafe.getInt(input, off))
      off         += 4
      unprocessed -= 4
    }

    if (unprocessed > 0) {
      var k1 = 0
      if (unprocessed == 3) {
        k1 ^= unsafe.getUnsignedByte(input, off + 2) << 16
      }
      if (unprocessed >= 2) {
        k1 ^= unsafe.getUnsignedByte(input, off + 1) << 8
      }
      if (unprocessed >= 1) {
        k1 ^= unsafe.getUnsignedByte(input, off)
        hash = fMix(hash, k1)
      }
    }

    avalanche(hash ^ length)
  }
}
