import java.lang.management.ManagementFactory
/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

/** package
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2019-03-29
  *   16:31
  */
package object jvm {
  private val runtime       = Runtime.getRuntime
  private val runtimeMXBean = ManagementFactory.getRuntimeMXBean

  val pid:                 Int  = runtimeMXBean.getName.split("@")(0).toInt
  val availableProcessors: Int  = runtime.availableProcessors()
  val startTime:           Long = runtimeMXBean.getStartTime
  def upTime:      Long = runtimeMXBean.getUptime
  def freeMemory:  Long = runtime.freeMemory()
  def maxMemory:   Long = runtime.maxMemory()
  def totalMemory: Long = runtime.totalMemory()
}
