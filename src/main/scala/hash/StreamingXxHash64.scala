package hash
import java.lang.Long.{rotateLeft => rotl64}

/** StreamingXxHash64
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2021/3/30
  *   4:12 下午
  */
object StreamingXxHash64 {
  def apply(seed: Long) = new StreamingXxHash64(seed)
}

final class StreamingXxHash64(seed: Long) extends StreamingHash[Long] {

  private[this] final val buffer = new Array[Byte](32)
  private[this] var v1          = seed + XxHash64.Prime1 + XxHash64.Prime2
  private[this] var v2          = seed + XxHash64.Prime2
  private[this] var v3          = seed
  private[this] var v4          = seed - XxHash64.Prime1
  private[this] var totalLength = 0
  private[this] var bufferSize  = 0

  def reset(): Unit = {
    v1 = seed + XxHash64.Prime1 + XxHash64.Prime2
    v2 = seed + XxHash64.Prime2
    v3 = seed
    v4 = seed - XxHash64.Prime1
    totalLength = 0
    bufferSize = 0
  }

  def value: Long = {
    var hash = 0L
    if (totalLength >= 32) {
      hash = rotl64(v1, 1) + rotl64(v2, 7) + rotl64(v3, 12) + rotl64(v4, 18)
      hash = XxHash64.mergeRound(hash, v1)
      hash = XxHash64.mergeRound(hash, v2)
      hash = XxHash64.mergeRound(hash, v3)
      hash = XxHash64.mergeRound(hash, v4)
    } else {
      hash = seed + XxHash64.Prime5
    }

    hash += totalLength

    XxHash64.finalize(hash, buffer, unsafe.ByteArrayBase, bufferSize)
  }

  private[hash] def update(input: Array[Byte], offset: Long, length: Int): Unit = {
    totalLength += length
    val newBuffSize = bufferSize + length
    if (newBuffSize < 32) {
      unsafe.copyMemory(input, offset, buffer, bufferSize + unsafe.ByteArrayBase, length)
      bufferSize = newBuffSize
    } else {
      var off         = offset
      var unprocessed = length
      if (bufferSize > 0) {
        val remaining = 32 - bufferSize
        unsafe.copyMemory(input, offset, buffer, bufferSize + unsafe.ByteArrayBase, remaining)

        v1 = XxHash64.round(v1, unsafe.getLong(buffer, unsafe.ByteArrayBase))
        v2 = XxHash64.round(v2, unsafe.getLong(buffer, unsafe.ByteArrayBase + 8L))
        v3 = XxHash64.round(v3, unsafe.getLong(buffer, unsafe.ByteArrayBase + 16L))
        v4 = XxHash64.round(v4, unsafe.getLong(buffer, unsafe.ByteArrayBase + 24L))

        off         += remaining
        unprocessed -= remaining
        bufferSize = 0
      }

      if (unprocessed >= 32) {
        do {
          v1 = XxHash64.round(v1, unsafe.getLong(input, off))
          v2 = XxHash64.round(v2, unsafe.getLong(input, off + 8L))
          v3 = XxHash64.round(v3, unsafe.getLong(input, off + 16L))
          v4 = XxHash64.round(v4, unsafe.getLong(input, off + 24L))

          off         += 32
          unprocessed -= 32
        } while (unprocessed >= 32)
      }

      if (unprocessed > 0) {
        unsafe.copyMemory(input, off, buffer, unsafe.ByteArrayBase, unprocessed)
        bufferSize = unprocessed
      }
    }
  }
}
