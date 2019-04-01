import java.time.LocalDate

import config.{Env, StandardTypesafeConfig}
import syntax.string._

import scala.collection.mutable.ListBuffer
import scala.compat.Platform.currentTime
/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

/**
  * App
  *
  * @author zhaihao
  * @version 1.0 26/01/2018 15:54
  */
trait App extends StandardTypesafeConfig {
  self: Env =>

  final val startTime: Long = currentTime

  private var _args: Array[String] = _

  private val initCode = new ListBuffer[() => Unit]
  private val year     = LocalDate.now().getYear

  val logo =
    """
       |/*                                                                         *\
       |**                  .__                                                    **
       |**    ____  _______ |__|  ______  ____    ____                             **
       |**   /  _ \ \_  __ \|  | /  ___/ /  _ \  /    \    ORISON.                 **
       |**  (  <_> ) |  | \/|  | \___ \ (  <_> )|   |  \   (c) 2017-2019           **
       |**   \____/  |__|   |__|/____  > \____/ |___|  /   https://orison.ooon.me  **
       |**                           \/              \/                            **""".stripMargin
  println(logo)
  println(
    s"**${"".padEnd(55, ' ')}env: [${Console.YELLOW}${env.getOrElse("").padStart(4, ' ')}${Console.RESET}]       **")
  println(s"\\*${"".padEnd(73, ' ')}*/\n")

  final def delayedInit(body: => Unit) {
    initCode += (() => body)
  }

  final def main(args: Array[String]) = {
    this._args = args
    for (proc <- initCode) proc()
    val total = currentTime - startTime
    println("\n[total " + total + "ms]")
  }
}
