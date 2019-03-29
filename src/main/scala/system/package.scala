import java.net.InetAddress

import com.sun.management.OperatingSystemMXBean
import sun.management.ManagementFactoryHelper
/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

/**
  * package
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-29 16:30
  */
package object system {
  private val bean: OperatingSystemMXBean =
    ManagementFactoryHelper.getOperatingSystemMXBean.asInstanceOf[OperatingSystemMXBean]

  val osName:                  String = bean.getName
  val osArch:                  String = bean.getArch
  val osVersion:               String = bean.getVersion
  val availableProcessors:     Int    = bean.getAvailableProcessors
  val totalPhysicalMemorySize: Long   = bean.getTotalPhysicalMemorySize

  def ip:                         String = InetAddress.getLocalHost.getHostAddress
  def hostname:                   String = InetAddress.getLocalHost.getHostName
  def freePhysicalMemorySize:     Long   = bean.getFreePhysicalMemorySize
  def committedVirtualMemorySize: Long   = bean.getCommittedVirtualMemorySize
  def freeSwapSpaceSize:          Long   = bean.getFreeSwapSpaceSize
  def processCpuTime:             Long   = bean.getProcessCpuTime
  def processCpuLoad:             Double = bean.getProcessCpuLoad
  def systemCpuLoad:              Double = bean.getSystemCpuLoad
  def systemLoadAverage:          Double = bean.getSystemLoadAverage
}
