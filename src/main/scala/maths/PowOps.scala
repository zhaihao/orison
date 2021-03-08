package maths

import java.io.File
import scala.language.implicitConversions

/**
  * PowOps
  *
  * @author zhaihao
  * @version 1.0
  * @since 2021/3/8 2:52 下午
  */
final class PowOps private[maths] (private val a: Double) extends AnyVal {
  def ^*(b: Double) = math.pow(a, b)
}

trait ToPowOps {
  @inline implicit def toPowOps[T](a: T)(implicit d: Numeric[T]): PowOps = new PowOps(d.toDouble(a))
}
