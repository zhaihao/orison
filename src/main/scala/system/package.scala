import java.net.InetAddress
import com.sun.management.OperatingSystemMXBean
import sun.jvmstat.monitor.{MonitoredHost, MonitoredVmUtil, VmIdentifier}
import sun.management.ManagementFactoryHelper

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
  *   16:30
  */
package object system {
//  private val osBean =
//    ManagementFactoryHelper.getOperatingSystemMXBean.asInstanceOf[OperatingSystemMXBean]
//
//  private val rtBean = ManagementFactoryHelper.getRuntimeMXBean
//
//  val osName:                  String = osBean.getName
//  val osArch:                  String = osBean.getArch
//  val osVersion:               String = osBean.getVersion
//  val availableProcessors:     Int    = osBean.getAvailableProcessors
//  val totalPhysicalMemorySize: Long   = osBean.getTotalPhysicalMemorySize
//  val pid:                     String = rtBean.getName.split("@").head
//
//  def ip:                         String = InetAddress.getLocalHost.getHostAddress
//  def hostname:                   String = InetAddress.getLocalHost.getHostName
//  def freePhysicalMemorySize:     Long   = osBean.getFreePhysicalMemorySize
//  def committedVirtualMemorySize: Long   = osBean.getCommittedVirtualMemorySize
//  def freeSwapSpaceSize:          Long   = osBean.getFreeSwapSpaceSize
//  def processCpuTime:             Long   = osBean.getProcessCpuTime
//  def processCpuLoad:             Double = osBean.getProcessCpuLoad
//  def systemCpuLoad:              Double = osBean.getSystemCpuLoad
//  def systemLoadAverage:          Double = osBean.getSystemLoadAverage
}
