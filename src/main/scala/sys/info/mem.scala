/*
 * Copyright (c) 2020-2022.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */
package sys.info

import oshi.util.Util
import squants.experimental.formatter.syntax._
import squants.information.InformationConversions._

import scala.concurrent.duration.{Duration, DurationInt}

/**
  * mem
  *
  * @author zhaihao
  * @version 1.0
  * @since 2022/7/30 01:51
  */
object mem {
  def total = memory.getTotal.bytes.inBestUnit.rounded(3)

  private[mem] class Usage(total: Long, used: Long, free: Long) {
    override def toString: String =
      f"Mem: ${total.bytes.inBestUnit.rounded(3)} total, ${free.bytes.inBestUnit.rounded(3)} free, ${used.bytes.inBestUnit.rounded(3)} used(${used*100.0/total}%2.2f%%)"
  }

  def usage(frequency: Duration = 1.seconds) = new Iterator[Usage] {
    private val long  = frequency.toMillis
    private val total = memory.getTotal

    override def hasNext: Boolean = true

    override def next(): Usage = {
      Util.sleep(long)
      val free = memory.getAvailable
      val used = total - free
      new Usage(total, used, free)
    }
  }
}
