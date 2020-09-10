package syntax

import scala.language.implicitConversions

/**
  * NumericOps
  *
  * @author zhaihao
  * @version 1.0
  * @since 2020/9/10 11:05 上午
  */
final class NumericOps[T: Numeric] private[syntax] (private val value: T) {
  import Numeric.Implicits._
  def *^(power: T) = scala.math.pow(value.toDouble(), power.toDouble())
}

trait ToNumericOps {
  @inline implicit def toNumericOps[T: Numeric](value: T) = new NumericOps[T](value)
}
