/*
 * Copyright (c) 2020-2022.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package until

import test.BaseSpec

/**
  * RetrySpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2022/7/20 20:46
  */
class RetrySpec extends BaseSpec {

  "retry" in {
    try {
      val a = util.retry(times = 5, delay = 1) {
        if (scala.util.Random.nextInt(100) % 20 != 0) throw new Exception("abc")
        else "hhh"
      }
      println(a)
    } catch {
      case _: Exception =>
    }

  }

  "retryErr" in {
    try {
      val a = util.retryErr(times = 5, delay = 1) {
        if (scala.util.Random.nextInt(100) % 20 != 0) throw new Exception("abc")
        else "hhh"
      } { case e: Exception =>
        e.printStackTrace()
      }
      println(a)
    } catch {
      case _: Exception =>
    }
  }
}
