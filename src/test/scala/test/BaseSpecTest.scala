/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package test

/**
  * BaseSpecTest
  *
  * @author zhaihao
  * @version 1.0 2017-12-8 11:47
  */
class BaseSpecTest extends BaseSpec {

  "base test trait" in {
    1 + 1 ==> 2

    1 + 2 !=> 2
  }
}
