/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package test

import org.scalatest._
import system._

import scala.sys.process._

/**
  * ProcessAsyncLike
  *
  * @author zhaihao
  * @version 1.0 2018-8-23 18:44
  */
trait ProcessAsyncLike extends AsyncTestSuiteMixin { this: AsyncTestSuite =>

  def className: Class[_]

  var process: Process = _

  abstract override def withFixture(test: NoArgAsyncTest) = {

    val out = ProcessLogger(line => println(line), line => println(line))

    process = proc.newProcess(className)(out)

    try super.withFixture(test)
    finally process.destroy()
  }
}
