/*
 * Copyright (c) 2020-2022.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package oos

import oshi.SystemInfo
import unit.formatter.DefaultFormatter
import unit.groups.information.IECInformation
import squants.information.Information

/**
  * package
  *
  * @author zhaihao
  * @version 1.0
  * @since 2022/7/30 01:51
  */
package object info {
  private val si              = new SystemInfo()
  private[info] val processor = si.getHardware.getProcessor
  private[info] val memory    = si.getHardware.getMemory
  implicit private[info] val informationFormatter: DefaultFormatter[Information] = new DefaultFormatter(IECInformation)
}
