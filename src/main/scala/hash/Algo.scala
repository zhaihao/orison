package hash

import java.io.InputStream
import java.io.Reader
import scala.io.{Source, Codec}

trait WithAlgo[A] {

  protected def withAlgo(algo: Algo): A

  def md2    = withAlgo(new Algo(() => new MessageDigest("MD2")))
  def md5    = withAlgo(new Algo(() => new MessageDigest("MD5")))
  def sha1   = withAlgo(new Algo(() => new MessageDigest("SHA-1")))
  def sha224 = withAlgo(new Algo(() => new MessageDigest("SHA-224")))
  def sha256 = withAlgo(new Algo(() => new MessageDigest("SHA-256")))
  def sha384 = withAlgo(new Algo(() => new MessageDigest("SHA-384")))
  def sha512 = withAlgo(new Algo(() => new MessageDigest("SHA-512")))
  def crc32  = withAlgo(new Algo(() => new CRC32Digest))
  def bcrypt = withAlgo(new Algo(() => new BCryptDigest(10)))
  def bcrypt(rounds: Int = 10) = withAlgo(new Algo(() => new BCryptDigest(rounds)))

  class HmacBuilder(val key: Array[Byte]) {
    def md5    = withAlgo(new Algo(() => new HMAC("HmacMD5", key)))
    def sha1   = withAlgo(new Algo(() => new HMAC("HmacSHA1", key)))
    def sha256 = withAlgo(new Algo(() => new HMAC("HmacSHA256", key)))
    def sha512 = withAlgo(new Algo(() => new HMAC("HmacSHA512", key)))
  }

  def hmac(key:    Array[Byte]) = new HmacBuilder(key)
  def hmac(key:    String) = new HmacBuilder(key.getBytes("UTF8"))
  def pbkdf2(salt: Array[Byte], iterations: Int, keyLength: Int): A = withAlgo(
    new Algo(() =>
      new Pbkdf2Digest(
        "PBKDF2WithHmacSHA1",
        salt,
        iterations,
        keyLength
      )
    )
  )

  def pbkdf2(salt: String, iterations: Int, keyLength: Int): A = pbkdf2(salt.getBytes("UTF8"), iterations, keyLength)
}

object Algo extends WithAlgo[Algo] {
  protected def withAlgo(algo: Algo): Algo = algo
}

class Algo private[hash] (
    private val digestBuilder: () => MutableDigest
) extends WithPlainText[Digest] {

  def digest: MutableDigest = digestBuilder()

  def name: String = digest.name

  override def toString = "Algo(%s)".format(name)

  override def apply(value: PlainText): Digest = value.fill(digest)

  def tap(value: InputStream, codec: Codec): InputStreamTap =
    new InputStreamTap(digest, value)

  def tap(value: InputStream): InputStreamTap = tap(value, Codec.UTF8)
  def tap(value: Reader, encoding: Codec): ReaderTap = new ReaderTap(digest, value, encoding)
  def tap(value: Reader): ReaderTap = tap(value, Codec.UTF8)
  def tap(value: Source, encoding: Codec): SourceTap = new SourceTap(digest, value, encoding)
  def tap(value: Source): SourceTap = tap(value, Codec.UTF8)
  def foldAble = new FoldAble(digest)
}
