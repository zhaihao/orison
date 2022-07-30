/*
 * Copyright (c) 2020-2022.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package oos.info

import oshi.util.Util

import scala.concurrent.duration.{Duration, DurationInt}

/**
  * cpu
  *
  * @author zhaihao
  * @version 1.0
  * @since 2022/7/29 20:35
  */
object cpu {

  private[cpu] class Usage(
      val us: Double,
      val ni: Double,
      val sy: Double,
      val id: Double,
      val wa: Double,
      val hi: Double,
      val si: Double,
      val st: Double
  ) {
    override def toString: String =
      f"%%Cpu: ${us * 100}%2.2f%% us, ${sy * 100}%2.2f%% sy, ${ni * 100}%2.2f%% ni, ${id * 100}%2.2f%% id, ${wa * 100}%2.2f%% wa, ${hi * 100}%2.2f%% hi, ${si * 100}%2.2f%% si, ${st * 100}%2.2f%% st"
  }

  private[cpu] class Load(m1: Double, m2: Double, m3: Double) {
    override def toString: String = f"Load average: $m1%2.2f, $m2%2.2f, $m3%2.2f"
  }

  def number = processor.getPhysicalProcessorCount

  def usage(frequency: Duration = 1.seconds) = new Iterator[Usage] {
    val long = frequency.toMillis
    var pre = processor.getSystemCpuLoadTicks

    override def hasNext: Boolean = true

    override def next(): Usage = {
      Util.sleep(long)
      val now   = processor.getSystemCpuLoadTicks
      val us    = now(0) - pre(0)
      val ni    = now(1) - pre(1)
      val sy    = now(2) - pre(2)
      val id    = now(3) - pre(3)
      val wa    = now(4) - pre(4)
      val hi    = now(5) - pre(5)
      val si    = now(6) - pre(6)
      val st    = now(7) - pre(7)
      val total = (us + ni + sy + id + wa + hi + si + st).toDouble
      pre = now
      if (total == 0) {
        new Usage(0, 0, 0, 1, 0, 0, 0, 0)
      } else {
        new Usage(us / total, ni / total, sy / total, id / total, wa / total, hi / total, si / total, st / total)
      }
    }
  }

  def load(frequency: Duration = 1.seconds) = new Iterator[Load] {
    val long = frequency.toMillis

    override def hasNext: Boolean = true

    override def next(): Load = {
      Util.sleep(long)
      val p = processor.getSystemLoadAverage(3)
      new Load(p(0), p(1), p(2))
    }
  }
}
