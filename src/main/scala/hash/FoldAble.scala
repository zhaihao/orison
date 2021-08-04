package hash

class FoldAble private[hash] (
    private val digest: MutableDigest
) extends WithPlainText[FoldAble] {

  private var consumed = false

  private def checkConsumption = synchronized {
    if (consumed) {
      throw new IllegalStateException("FoldAble has already been consumed")
    }
    consumed = true
  }

  override def apply(value: PlainText): FoldAble = {
    checkConsumption
    value.fill(digest)
    new FoldAble(digest)
  }

  def done: Digest = {
    checkConsumption
    digest
  }
}
