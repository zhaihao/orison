package hash

object Hasher extends WithPlainText[Hasher] {

  def apply(value: PlainText): Hasher = new Hasher(value)
}

class Hasher private (
    private val value: PlainText
) extends WithAlgo[Digest] {

  def salt(saltValue: Array[Byte]): Hasher =
    Hasher(new PlainTextSalt(value, saltValue))

  def salt(saltValue: String): Hasher = salt(saltValue.getBytes("UTF8"))

  override protected def withAlgo(algo: Algo): Digest =
    value.fill(algo.digest)
}
