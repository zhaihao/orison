/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package orison

import java.time.LocalDate

import com.typesafe.scalalogging.StrictLogging
import config.HConfig
import syntax.string._
import console.Colors._
import scala.collection.mutable.ListBuffer
import scala.compat.Platform.currentTime
import squants.experimental.formatter.DefaultFormatter
import squants.experimental.formatter.syntax._
import squants.experimental.unitgroups.si.expanded.time.ExpandedSiTimes
import squants.time.TimeConversions._

/**
  * App
  *
  * @author zhaihao
  * @version 1.0 26/01/2018 15:54
  */
trait App extends StrictLogging { config: HConfig =>
  final val startTime: Long = System.currentTimeMillis

  private var _args: Array[String] = _

  private val initCode = new ListBuffer[() => Unit]
  private val year     = LocalDate.now().getYear

  // format: off
  val logo =
    s"""
  |${white("/*")}                                                                         ${white("")}${white("*\\")}
  |${white("**")}                  ${RB_YELLOW(".__")}                                                    ${white("**")}
  |${white("**")}    ${RB_RED("____")}  ${RB_ORANGE("_______ ")}${RB_YELLOW("|__|")}  ${RB_GREEN("______")}  ${RB_INDIGO("____")}    ${RB_VIOLET("____")}                             ${white("**")}
  |${white("**")}   ${RB_RED("/  _ \\")} ${RB_ORANGE("\\_  __ \\")}${RB_YELLOW("|  |")} ${RB_GREEN("/  ___/")} ${RB_INDIGO("/  _ \\")}  ${RB_VIOLET("/    \\")}    ORISON.                 ${white("**")}
  |${white("**")}  ${RB_RED("(  <_> )")} ${RB_ORANGE("|  | \\/")}${RB_YELLOW("|  |")} ${RB_GREEN("\\___ \\")} ${RB_INDIGO("(  <_> )")}${RB_VIOLET("|   |  \\")}   (c) 2017-$year           ${white("**")}
  |${white("**")}   ${RB_RED("\\____/")}  ${RB_ORANGE("|__|")}   ${RB_YELLOW("|__|")}${RB_GREEN("/____  >")} ${RB_INDIGO("\\____/")} ${RB_VIOLET("|___|  /")}   https://orison.ooon.me  ${white("**")}
  |${white("**")}                          ${RB_GREEN("\\/")}              ${RB_VIOLET("\\/")}                             ${white("**")}
  |${white("**")}${"".padEnd(55, ' ')}env: [${RB_ORANGE(env.padStart(4, ' '))}]       ${white("**")}
  |${white("\\*")}${"".padEnd(73, ' ')}${white("*/")}
  |""".stripMargin
  // format: off
  logo.split("\n").foreach(line => logger.info(line))
  logger.info("")

  final def delayedInit(body: => Unit) = {
    initCode += (() => body)
  }

  final def main(args: Array[String]) = {
    this._args = args
    for (proc <- initCode) proc()
    implicit val timeFormatter = new DefaultFormatter(ExpandedSiTimes)
    val total                  = (System.currentTimeMillis - startTime).milliseconds.inBestUnit.rounded(3)
    logger.info("[run total " + total + "]")
  }
}
