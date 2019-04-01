/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package test

import scala.concurrent.Future

/**
  * BaseAsyncSpecTest
  *
  * @author zhaihao
  * @version 1.0 2018-03-21 14:28
  */
class BaseAsyncSpecTest extends BaseAsyncSpec {

  def addSoon(xs: Int*): Future[Int] = Future { xs.sum }

  "async test" in {
    val f = addSoon(1, 2, 3, 4, 5)
    f.map(_ ==> 15)
  }

  "sync test" in {
    // 在BaseAsyncSpec 中必须以 Assertions 对象返回
    1 ==> 1
  }
}
