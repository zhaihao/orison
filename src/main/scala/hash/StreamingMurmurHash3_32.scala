package hash

/** StreamingMurmurHash3_32
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2021/3/30
  *   4:07 下午
  */
object StreamingMurmurHash3_32 {
  def apply(seed: Int) = new StreamingMurmurHash3_32(seed)
}

final class StreamingMurmurHash3_32(seed: Int) extends StreamingHash[Int] {

  private[this] final val buffer = new Array[Byte](4)
  private[this] var hash        = seed
  private[this] var totalLength = 0
  private[this] var bufferSize  = 0

  def reset(): Unit = {
    totalLength = 0
    bufferSize = 0
    hash = seed
  }

  def value: Int = {
    var h = hash
    if (bufferSize > 0) {
      var k1 = 0
      if (bufferSize == 3) {
        k1 ^= unsafe.getUnsignedByte(buffer, 18L) << 16
      }
      if (bufferSize >= 2) {
        k1 ^= unsafe.getUnsignedByte(buffer, 17L) << 8
      }
      if (bufferSize >= 1) {
        k1 ^= unsafe.getUnsignedByte(buffer, 16L)
        h = MurmurHash3_32.fMix(h, k1)
      }
    }

    MurmurHash3_32.avalanche(h ^ totalLength)
  }

  private[hash] def update(input: Array[Byte], offset: Long, length: Int): Unit = {
    totalLength += length
    val newBuffSize = bufferSize + length
    if (newBuffSize < 4) {
      unsafe.copyMemory(input, offset, buffer, bufferSize + unsafe.ByteArrayBase, length)
      bufferSize = newBuffSize
    } else {
      var off         = offset
      var unprocessed = length
      if (bufferSize > 0) {
        val remaining = 4 - bufferSize
        unsafe.copyMemory(input, offset, buffer, bufferSize + unsafe.ByteArrayBase, remaining)
        hash = MurmurHash3_32.mix(hash, unsafe.getInt(buffer, unsafe.ByteArrayBase))
        off         += remaining
        unprocessed -= remaining
        bufferSize = 0
      }

      if (unprocessed >= 4) {
        do {
          hash = MurmurHash3_32.mix(hash, unsafe.getInt(input, off))
          off         += 4
          unprocessed -= 4
        } while (unprocessed >= 4)
      }

      if (unprocessed > 0) {
        unsafe.copyMemory(input, off, buffer, unsafe.ByteArrayBase, unprocessed)
        bufferSize = unprocessed
      }
    }
  }
}
