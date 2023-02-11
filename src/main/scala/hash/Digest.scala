package hash

import scala.language.implicitConversions

object Digest {

  private[hash] def compare(a: Array[Byte], b: Array[Byte]) = {
    // I opted for writing my own instead of using the MessageDigest
    // function because of this article:
    // http://codahale.com/a-lesson-in-timing-attacks/
    // Yes, the bug was fixed, but this means better support for someone
    // using an old version
    if (a.length != b.length) false
    else {
      a.indices.foldLeft(0) { (accum, i) =>
        accum | (a(i) ^ b(i))
      } == 0
    }
  }

  implicit def digest2hash(in:      Digest): Hash        = in.hash
  implicit def digest2string(in:    Digest): String      = in.hex
  implicit def digest2byteArray(in: Digest): Array[Byte] = in.bytes
}

trait Digest {

  def name: String
  def hash: Hash

  /** Determines whether the collected bytes compute to a given hash */
  def `hash_=`(vs: Hash): Boolean

  def `hash_=`(vs: String): Boolean = {
    try {
      hash_=(Hash(vs))
    } catch {
      case _: NumberFormatException => false
    }
  }

  def `hash_=`(vs: Array[Byte]): Boolean = hash_=(Hash(vs))

  def `hash_=`(vs: Digest): Boolean = hash_=(vs.hash)

  def hex: String = hash.hex

  def bytes: Array[Byte] = hash.bytes

  override def toString = "Digest(%s, %s)".format(name, hash.hex)
}

trait MutableDigest extends Digest {

  def add(bytes: Array[Byte], length: Int): MutableDigest
}

private class MessageDigest(
    override val name: String
) extends MutableDigest {

  import java.security.{MessageDigest => jMessageDigest}

  private val jDigest = jMessageDigest.getInstance(name)

  override def add(bytes: Array[Byte], length: Int): MutableDigest = {
    jDigest.update(bytes, 0, length)
    this
  }

  override def hash: Hash = {
    val clone = jDigest.clone.asInstanceOf[jMessageDigest]
    Hash(clone.digest)
  }

  override def `hash_=`(vs: Hash): Boolean =
    Digest.compare(jDigest.digest, vs.bytes)
}

private class HMAC(
    override val name: String,
    key:               Array[Byte]
) extends MutableDigest {

  import javax.crypto.Mac
  import javax.crypto.spec.SecretKeySpec

  private val mac = Mac.getInstance(name)

  mac.init(new SecretKeySpec(key, name))

  override def add(bytes: Array[Byte], length: Int): MutableDigest = {
    mac.update(bytes, 0, length)
    this
  }

  override def hash: Hash = {
    Hash(mac.clone.asInstanceOf[Mac].doFinal)
  }

  override def `hash_=`(vs: Hash): Boolean =
    Digest.compare(mac.clone.asInstanceOf[Mac].doFinal, vs.bytes)
}

private class CRC32Digest extends MutableDigest {

  import java.util.zip.CRC32

  private val digest = new CRC32

  override val name = "CRC32"

  override def add(bytes: Array[Byte], length: Int): MutableDigest = {
    digest.update(bytes, 0, length)
    this
  }

  override def hash: Hash = {

    import java.nio.ByteBuffer

    val bytes = ByteBuffer.allocate(8).putLong(digest.getValue).array

    val toTrim = bytes.indexWhere(_ != 0) match {
      case -1     => 4
      case zeroes => zeroes.min(bytes.length - 4)
    }

    Hash(bytes.drop(toTrim))
  }

  override def `hash_=`(vs: Hash): Boolean =
    Digest.compare(hash.bytes, vs.bytes)
}

private class BCryptDigest(
    private val rounds: Int
) extends MutableDigest {

  import org.mindrot.jbcrypt.{BCrypt => jBCrypt}

  private val value = new StringBuilder

  override val name = "BCrypt"

  override def add(bytes: Array[Byte], length: Int): MutableDigest = {
    value.append(new String(bytes, 0, length, "UTF8"))
    this
  }

  override def hash: Hash = {
    Hash(
      jBCrypt
        .hashpw(
          value.toString,
          jBCrypt.gensalt(rounds)
        )
        .getBytes("UTF8")
    )
  }

  override def `hash_=`(vs: Hash): Boolean = {
    // jBCrypt chokes on empty hashes, so we compensate
    new String(vs.bytes) match {
      case ""  => false
      case str => jBCrypt.checkpw(value.toString, str)
    }
  }
}

private class Pbkdf2Digest(
    override val name:      String,
    private val salt:       Array[Byte],
    private val iterations: Int,
    private val length:     Int
) extends MutableDigest {

  import javax.crypto.spec.PBEKeySpec
  import javax.crypto.SecretKeyFactory

  private val value = new StringBuilder

  private val factory = SecretKeyFactory.getInstance(name);

  override def add(bytes: Array[Byte], length: Int): MutableDigest = {
    value.append(new String(bytes, 0, length, "UTF8"))
    this
  }

  override def hash: Hash = {
    val keySpec = new PBEKeySpec(
      value.toString.toCharArray,
      salt,
      iterations,
      length
    );
    Hash(factory.generateSecret(keySpec).getEncoded)
  }

  override def `hash_=`(vs: Hash): Boolean =
    Digest.compare(hash.bytes, vs.bytes)
}
