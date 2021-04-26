import maths.ToPowOps

/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

/**
  * package
  *
  * @author zhaihao
  * @version 1.0 2019-01-07 10:54
  */
package object maths extends maths {

  /**
    * 支持 BigDecimal 的 e
    *
    * @param x 指数
    * @param p 求 e 的精度，迭代的次数
    * @return
    */
  def exp(x: BigDecimal, p: Int): BigDecimal =
    LazyList
      .from(1)
      .takeWhile(_ <= p)
      .foldLeft((BigDecimal(1), BigDecimal(1)) -> BigDecimal(1)) {
        case (((fac, pow), res), n) =>
          val f = fac * n
          val p = pow * x
          ((f, p), res + p / f)
      }
      ._2

  def sigmoid(x: Double) = 1.0 / (1.0 + scala.math.exp(-x))

  def sigmoid(x: BigDecimal, p: Int) = 1.0 / (1.0 + exp(-x, p))
}

trait maths extends ToPowOps
