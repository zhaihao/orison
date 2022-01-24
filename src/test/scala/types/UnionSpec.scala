/*
 * Copyright (c) 2022.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package types

import org.scalatest.matchers.must.Matchers
import test.BaseSpec
import types.|._

import java.io.File

/**
  * UnionSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2022/1/25 01:01
  */
class UnionSpec extends BaseSpec with Matchers {

  "基本类型" in {
    val x1: Int | String = 4
    x1 ==> 4
    x1 must be(an[Int])
    x1 must not be a[String]

    val x2: Int | String = "hello"
    x2 ==> "hello"
    x2 must be(a[String])
    x2 must not be an[Int]

    val x3: Float | Double = 3f
    x3 must be(a[Float])
    x3 must not be a[Double]

    val x4: File | Double = 2.0
    x4 must be(a[Double])
    x4 must not be a[File]
  }

  "3种类型" in {
    val x1: Int | File | String = 3
    x1 must be(an[Int])
    x1 must not be a[File]
    x1 must not be a[String]
  }

  "只能使用 object 方法 或者 match" in {
    val x1: Int | String = "444"
    x1 match {
      case a: Int    => println(a.toDouble)
      case a: String => println(a.substring(0, 2))
    }

    x1.asInstanceOf[String] // 只在确定类型时才能强制转换，通常来说也是没意义的
    assertThrows[ClassCastException](x1.asInstanceOf[Int])
  }

  "集合类型" in {
    val x1: Seq[Int] | CharSequence = List(1, 2, 3)
    x1 ==> List(1, 2, 3)
    x1 must be(a[List[_]]) // scala test 无法做两层校验
    x1 must not be an[CharSequence]

    val x2: Seq[Int] | Seq[String] = List(1, 2, 3)
    x2 ==> List(1, 2, 3)
  }

}
