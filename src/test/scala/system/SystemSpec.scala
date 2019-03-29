/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package system
import test.BaseSpec

/**
  * SystemSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-29 16:59
  */
class SystemSpec extends BaseSpec {

  "osName" in { println(system.osName) }

  "osArch" in { println(system.osArch) }

  "osVersion" in { println(system.osVersion) }

  "ip" in { println(system.ip) }

  "hostname" in { println(system.hostname) }

  "availableProcessors" in { println(system.availableProcessors) }

  "totalPhysicalMemorySize" in { println(system.totalPhysicalMemorySize) }

  "freePhysicalMemorySize" in { println(system.freePhysicalMemorySize) }

  "committedVirtualMemorySize" in { println(system.committedVirtualMemorySize) }

  "freeSwapSpaceSize" in { println(system.freeSwapSpaceSize) }

  "processCpuTime" in { println(system.processCpuTime) }

  "processCpuLoad" in { println(system.processCpuLoad) }

  "systemCpuLoad" in { println(system.systemCpuLoad) }

  "systemLoadAverage" in { println(system.systemLoadAverage) }
}
