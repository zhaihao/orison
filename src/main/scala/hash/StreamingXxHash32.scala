package hash
import java.lang.Integer.{rotateLeft => rotl32}

/** StreamingXxHash32
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2021/3/30
  *   4:11 下午
  */
object StreamingXxHash32 {
  def apply(seed: Int) = new StreamingXxHash32(seed)
}

final class StreamingXxHash32(seed: Int) extends StreamingHash[Int] {

  private[this] final val buffer = new Array[Byte](16)
  private[this] var v1          = seed + XxHash32.Prime1 + XxHash32.Prime2
  private[this] var v2          = seed + XxHash32.Prime2
  private[this] var v3          = seed
  private[this] var v4          = seed - XxHash32.Prime1
  private[this] var totalLength = 0
  private[this] var bufferSize  = 0

  def reset(): Unit = {
    v1 = seed + XxHash32.Prime1 + XxHash32.Prime2
    v2 = seed + XxHash32.Prime2
    v3 = seed
    v4 = seed - XxHash32.Prime1
    totalLength = 0
    bufferSize = 0
  }

  def value: Int = {
    var hash = 0
    if (totalLength >= 16) {
      hash = rotl32(v1, 1) + rotl32(v2, 7) + rotl32(v3, 12) + rotl32(v4, 18)
    } else {
      hash = seed + XxHash32.Prime5
    }

    hash += totalLength

    XxHash32.finalize(hash, buffer, unsafe.ByteArrayBase, bufferSize)
  }

  private[hash] def update(input: Array[Byte], offset: Long, length: Int): Unit = {
    totalLength += length
    val newBuffSize = bufferSize + length
    if (newBuffSize < 16) {
      unsafe.copyMemory(input, offset, buffer, bufferSize + unsafe.ByteArrayBase, length)
      bufferSize = newBuffSize
    } else {
      var off         = offset
      var unprocessed = length
      if (bufferSize > 0) {
        val remaining = 16 - bufferSize
        unsafe.copyMemory(input, offset, buffer, bufferSize + unsafe.ByteArrayBase, remaining)

        v1 = XxHash32.round(v1, unsafe.getInt(buffer, unsafe.ByteArrayBase))
        v2 = XxHash32.round(v2, unsafe.getInt(buffer, unsafe.ByteArrayBase + 4L))
        v3 = XxHash32.round(v3, unsafe.getInt(buffer, unsafe.ByteArrayBase + 8L))
        v4 = XxHash32.round(v4, unsafe.getInt(buffer, unsafe.ByteArrayBase + 12L))

        off         += remaining
        unprocessed -= remaining
        bufferSize = 0
      }

      if (unprocessed >= 16) {
        do {
          v1 = XxHash32.round(v1, unsafe.getInt(input, off))
          v2 = XxHash32.round(v2, unsafe.getInt(input, off + 4L))
          v3 = XxHash32.round(v3, unsafe.getInt(input, off + 8L))
          v4 = XxHash32.round(v4, unsafe.getInt(input, off + 12L))

          off         += 16
          unprocessed -= 16
        } while (unprocessed >= 16)
      }

      if (unprocessed > 0) {
        unsafe.copyMemory(input, off, buffer, unsafe.ByteArrayBase, unprocessed)
        bufferSize = unprocessed
      }
    }
  }
}
