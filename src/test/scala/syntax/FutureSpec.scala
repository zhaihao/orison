/*
 * Copyright (c) 2021.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package syntax

import test.BaseSpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.util.Try

/** FutureSpec
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2021/4/28
  *   18:24
  */
class FutureSpec extends BaseSpec {
  import syntax.feature._
  "await" in {
    implicit val timeout = 3.seconds
    Future { 1 + 1 }.valued    ==> 2
    Future { 1 + 1 }.tryValued ==> Try(2)
  }
}
