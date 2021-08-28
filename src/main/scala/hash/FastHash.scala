package hash
import java.nio.ByteBuffer
import sun.nio.ch.DirectBuffer

/** FastHashUtil
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2021/3/30
  *   3:55 下午
  */
object FastHashUtil {
  final def checkBounds(inputLength: Int, offset: Int, length: Int): Unit =
    if (offset < 0 || length < 0 || inputLength - offset < length) {
      throw new IndexOutOfBoundsException()
    }
}

trait FastHash[T] {
  def hashByte(input: Byte, seed: T): T
  def hashInt(input:  Int, seed:  T): T
  def hashLong(input: Long, seed: T): T

  final def hashByteArray(input: Array[Byte], seed: T): T =
    hashBytes(input, unsafe.ByteArrayBase, input.length, seed)

  final def hashByteArray(input: Array[Byte], offset: Int, length: Int, seed: T): T = {
    FastHashUtil.checkBounds(input.length, offset, length)
    hashBytes(input, unsafe.ByteArrayBase + offset, length, seed)
  }

  final def hashByteBuffer(input: ByteBuffer, seed: T): T =
    if (input.hasArray) {
      hashBytes(input.array, unsafe.ByteArrayBase + input.arrayOffset, input.capacity, seed)
    } else {
      hashBytes(null, input.asInstanceOf[DirectBuffer].address, input.capacity, seed)
    }

  final def hashByteBuffer(input: ByteBuffer, offset: Int, length: Int, seed: T): T = {
    FastHashUtil.checkBounds(input.capacity, offset, length)
    if (input.hasArray) {
      hashBytes(input.array, unsafe.ByteArrayBase + input.arrayOffset + offset, length, seed)
    } else {
      hashBytes(null, input.asInstanceOf[DirectBuffer].address + offset, length, seed)
    }
  }

  private[hash] def hashBytes(input: Array[Byte], offset: Long, length: Int, seed: T): T
}

trait StreamingHash[T] {
  def reset(): Unit
  def value:   T

  final def updateByteArray(input: Array[Byte], offset: Int, length: Int): Unit = {
    FastHashUtil.checkBounds(input.length, offset, length)
    update(input, unsafe.ByteArrayBase + offset, length)
  }

  final def updateByteBuffer(input: ByteBuffer, offset: Int, length: Int): Unit = {
    FastHashUtil.checkBounds(input.capacity, offset, length)
    if (input.hasArray) {
      update(input.array, unsafe.ByteArrayBase + input.arrayOffset + offset, length)
    } else {
      update(null, input.asInstanceOf[DirectBuffer].address + offset, length)
    }
  }

  private[hash] def update(input: Array[Byte], offset: Long, length: Int): Unit
}
