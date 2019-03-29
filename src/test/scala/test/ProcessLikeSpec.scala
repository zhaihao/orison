/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package test

/**
  * ProcessLikeSpec
  *
  * @author zhaihao
  * @version 1.0 2018/8/23 19:25
  */
class ProcessLikeSpec extends BaseSpec with ProcessLike {
  "test" ignore  {
    println("main process")
    // wait for sub process start
    Thread.sleep(500)
  }

  override def className = ProcessLikeSpecHold.getClass
}

object ProcessLikeSpecHold {
  def main(args: Array[String]): Unit = {
    println("test process")
  }
}
