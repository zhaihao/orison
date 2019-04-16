/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package orison

import java.time.LocalDate

import com.typesafe.scalalogging.StrictLogging
import config.{Env, StandardTypesafeConfig}

import scala.collection.mutable.ListBuffer
import scala.compat.Platform.currentTime
import syntax.string._

/**
  * App
  *
  * @author zhaihao
  * @version 1.0 26/01/2018 15:54
  */
trait App extends StandardTypesafeConfig with StrictLogging {
  self: Env =>

  final val startTime: Long = currentTime

  private var _args: Array[String] = _

  private val initCode = new ListBuffer[() => Unit]
  private val year     = LocalDate.now().getYear

  val logo =
    s"""
       |/*                                                                         *\\
       |**                  .__                                                    **
       |**    ____  _______ |__|  ______  ____    ____                             **
       |**   /  _ \\ \\_  __ \\|  | /  ___/ /  _ \\  /    \\    ORISON.                 **
       |**  (  <_> ) |  | \\/|  | \\___ \\ (  <_> )|   |  \\   (c) 2017-$year           **
       |**   \\____/  |__|   |__|/____  > \\____/ |___|  /   https://orison.ooon.me  **
       |**                          \\/              \\/                             **
       |**${"".padEnd(55, ' ')}env: [${env
         .getOrElse("")
         .padStart(4, ' ')}]       **
       |\\*${"".padEnd(73, ' ')}*/""".stripMargin
  logo.split("\n").foreach(line => logger.info(line))

  final def delayedInit(body: => Unit) {
    initCode += (() => body)
  }

  final def main(args: Array[String]) = {
    this._args = args
    for (proc <- initCode) proc()
    val total = currentTime - startTime
    logger.info("[total " + total + "ms]")
  }
}
