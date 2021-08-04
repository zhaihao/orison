package hash

import scala.io.{Source, Codec}

import java.io.InputStream
import java.io.Reader

/** A tap is a specialized digest that decorates streams */
trait Tap extends Digest

/** A tap that buffers the characters before writing to the digest */
trait BufferedTap extends Tap {

  import scala.collection.mutable.ArrayBuffer

  /** The digest to write to */
  protected def digest: MutableDigest

  /** The buffered data */
  private val buffer = new ArrayBuffer[Byte]

  override def name: String = digest.name

  /** Flushes the buffer to the digest */
  private def flush: Unit = {
    if (buffer.nonEmpty) {
      val data = buffer.clone.toArray
      digest.add(data, data.length)
      buffer.dropInPlace(data.length)
    }
  }

  /** Adds a byte to the digest */
  protected def addByteToDigest(byte: Byte): Byte = {
    buffer += byte
    if (buffer.size >= 1024) flush
    byte
  }

  override def hash: Hash = {
    flush
    digest.hash
  }

  override def `hash_=`(vs: Hash): Boolean = {
    flush
    digest.hash_=(vs)
  }
}

/** An InputStream that generates a hash */
class InputStreamTap(
    override protected val digest: MutableDigest,
    private val stream:            InputStream
) extends InputStream
    with BufferedTap {

  override def read: Int = {
    val byte = stream.read
    if (byte >= 0) addByteToDigest(byte.toByte)
    byte
  }

  override def available = stream.available

  override def close = stream.close

  override def markSupported = false

  override def mark(readLimit: Int) = throw new UnsupportedOperationException

  override def reset = throw new UnsupportedOperationException
}

class ReaderTap(
    protected val digest: MutableDigest,
    private val reader:   Reader,
    private val codec:    Codec
) extends Reader
    with Tap {

  import scala.annotation.tailrec

  override def name: String = digest.name

  override def hash: Hash = digest.hash

  override def `hash_=`(vs: Hash): Boolean = digest.hash_=(vs)

  override def read(buf: Array[Char], off: Int, len: Int): Int = {
    val read = reader.read(buf, off, len)

    if (read > 0) {
      val str   = new String(buf, off, read)
      val bytes = str.getBytes(codec.charSet)
      digest.add(bytes, bytes.length)
    }

    read
  }

  override def ready = reader.ready

  override def close = reader.close

  override def markSupported = false

  override def mark(readLimit: Int) = throw new UnsupportedOperationException

  override def reset = throw new UnsupportedOperationException

  /** Converts this reader to a string */
  def mkString: String = {

    val result = new StringBuilder
    val buffer = new Array[Char](1024)

    @tailrec def build: Unit = {
      val count = read(buffer, 0, 1024)
      if (count == -1) {
        close
      } else {
        result.appendAll(buffer, 0, count)
        build
      }
    }

    build

    result.toString()
  }
}

/** Wraps a source and generates a Source as data flows through it */
class SourceTap(
    override protected val digest: MutableDigest,
    private val source:            Source,
    private val codec:             Codec
) extends Source
    with BufferedTap {

  override protected val iter = new Iterator[Char] {

    override def hasNext = source.hasNext

    override def next(): Char = {
      val next = source.next()
      val str  = Character.toString(next)
      str.getBytes(codec.charSet).foreach(addByteToDigest)
      next
    }

  }
}
