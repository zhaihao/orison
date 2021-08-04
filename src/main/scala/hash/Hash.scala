package hash

import scala.language.implicitConversions

object Hash {

  def apply(string: String): Hash = {
    val hex = if (string.length % 2 != 0) "0" + string else string

    new Hash(hex.grouped(2).map(Integer.parseInt(_, 16).byteValue).toArray)
  }

  implicit def hashToString(from: Hash): String = from.hex

  implicit def hashToByteArray(from: Hash): Array[Byte] = from.bytes

  //noinspection SpellCheckingInspection
  private[hash] val HexChars = "0123456789abcdef".toCharArray
}

case class Hash(bytes: Array[Byte]) extends Equals {

  lazy val hex: String = {
    val buffer = new StringBuilder(bytes.length * 2)
    bytes.foreach { byte =>
      buffer.append(Hash.HexChars((byte & 0xf0) >> 4))
      buffer.append(Hash.HexChars(byte & 0x0f))
    }
    buffer.toString
  }

  override def toString: String = hex

  override def hashCode: Int = hex.hashCode

  override def equals(other: Any) = other match {
    case str:  String                      => equals(Hash(str))
    case hash: Hash if hash.canEqual(this) => Digest.compare(bytes, hash.bytes)
    case ary:  Array[Byte]                 => Digest.compare(bytes, ary)
    case _ => false
  }

  override def canEqual(other: Any) = other.isInstanceOf[Hash]
}
