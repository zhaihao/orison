/*
 * Copyright (c) 2020-2022.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package oos.info

import oshi.util.Util
import squants.experimental.formatter.syntax._
import squants.information.InformationConversions._

import scala.concurrent.duration.{Duration, DurationInt}

/**
  * jvm
  *
  * @author zhaihao
  * @version 1.0
  * @since 2022/7/30 13:27
  */
object jvm {
  private[jvm] class Usage(val total: Long, val used: Long, val free: Long) {
    override def toString: String =
      f"Mem: ${total.bytes.inBestUnit.rounded(3)} total, ${free.bytes.inBestUnit.rounded(3)} free, ${used.bytes.inBestUnit
        .rounded(3)} used(${used * 100.0 / total}%2.2f%%)"
  }

  private val runtime = Runtime.getRuntime
  def total = runtime.totalMemory().bytes.inBestUnit.rounded(3)

  def usage(frequency: Duration = 1.seconds) = new Iterator[Usage] {
    private val long  = frequency.toMillis
    private val total = runtime.totalMemory()

    override def hasNext: Boolean = true

    override def next(): Usage = {
      Util.sleep(long)
      val free = runtime.freeMemory()
      val used = total - free
      new Usage(total, used, free)
    }
  }
}
