package hash
import java.lang.Integer.{rotateLeft => rotl32}

/** XxHash32
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2021/3/30
  *   4:08 下午
  */
object XxHash32 extends FastHash[Int] {
  val Prime1: Int = -1640531535
  val Prime2: Int = -2048144777
  val Prime3: Int = -1028477379
  val Prime4: Int = 668265263
  val Prime5: Int = 374761393

  final def hashByte(input: Byte, seed: Int): Int =
    avalanche(processByte(seed + Prime5 + 1, input & 0xff))

  final def hashInt(input: Int, seed: Int): Int =
    avalanche(processInt(seed + Prime5 + 4, input))

  final def hashLong(input: Long, seed: Int): Int =
    avalanche(processInt(processInt(seed + Prime5 + 8, input.asInstanceOf[Int]), (input >> 32).asInstanceOf[Int]))

  private[hash] final def round(acc: Int, input: Int): Int =
    rotl32(acc + input * Prime2, 13) * Prime1

  private[hash] final def finalize(hash: Int, input: Array[Byte], offset: Long, length: Int): Int = {
    var h           = hash
    var off         = offset
    var unprocessed = length

    while (unprocessed >= 4) {
      h = processInt(h, unsafe.getInt(input, off))
      off         += 4
      unprocessed -= 4
    }

    while (unprocessed > 0) {
      h = processByte(h, unsafe.getUnsignedByte(input, off))
      off         += 1
      unprocessed -= 1
    }

    avalanche(h)
  }

  private[hash] final def hashBytes(input: Array[Byte], offset: Long, length: Int, seed: Int): Int = {
    var hash        = 0
    var off         = offset
    var unprocessed = length

    if (length >= 16) {
      var v1 = seed + Prime1 + Prime2
      var v2 = seed + Prime2
      var v3 = seed
      var v4 = seed - Prime1

//      do {
//        v1 = round(v1, unsafe.getInt(input, off))
//        v2 = round(v2, unsafe.getInt(input, off + 4L))
//        v3 = round(v3, unsafe.getInt(input, off + 8L))
//        v4 = round(v4, unsafe.getInt(input, off + 12L))
//
//        off         += 16
//        unprocessed -= 16
//      } while (unprocessed >= 16)

      while {
        v1 = round(v1, unsafe.getInt(input, off))
        v2 = round(v2, unsafe.getInt(input, off + 4L))
        v3 = round(v3, unsafe.getInt(input, off + 8L))
        v4 = round(v4, unsafe.getInt(input, off + 12L))
        off         += 16
        unprocessed -= 16

        unprocessed >= 16
      } do ()

      hash = rotl32(v1, 1) + rotl32(v2, 7) + rotl32(v3, 12) + rotl32(v4, 18)

    } else {
      hash = seed + Prime5
    }

    hash += length

    finalize(hash, input, off, unprocessed)
  }

  private final def processByte(hash: Int, input: Int): Int =
    rotl32(hash + input * Prime5, 11) * Prime1

  private final def processInt(hash: Int, input: Int): Int =
    rotl32(hash + input * Prime3, 17) * Prime4

  private final def avalanche(hash: Int): Int = {
    val k1 = (hash ^ (hash >>> 15)) * Prime2
    val k2 = (k1 ^ (k1 >>> 13)) * Prime3
    k2 ^ (k2 >>> 16)
  }
}
