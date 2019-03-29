/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package system.proc
import system._
import test.BaseSpec

import scala.sys.process.ProcessLogger

/**
  * ProcSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-29 17:10
  */
class ProcSpec extends BaseSpec {
  "start another jvm process" ignore {
    println("caller process")
    val out     = ProcessLogger(line => println(line), line => println(line))
    val process = proc.newProcess(TestProcess.getClass)(out)
    Thread.sleep(500)
    process.destroy()
    process.exitValue() ==> 143
  }

  "get pid" ignore {
    println("caller process")
    val out     = ProcessLogger(line => println(line), line => println(line))
    val process = proc.newProcess(TestProcess.getClass)(out)
    println(proc.pid(process))
    Thread.sleep(500)
    process.destroy()
  }
}

object TestProcess {

  def main(args: Array[String]): Unit = {
    println("test process")
    Thread.sleep(60 * 1000)
  }
}
