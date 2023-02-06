/*
 * Copyright (c) 2020-2022.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package oos.info

import com.typesafe.scalalogging.StrictLogging
import test.BaseSpec

import scala.concurrent.duration.DurationInt

/**
  * CpuSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2022/7/29 20:46
  */
class CpuSpec extends BaseSpec with StrictLogging {

  "cpu 静态数据" in {
    logger.info(s"CPU核心数: ${cpu.number}")
  }
  "cpu 使用率" in {
    cpu.usage(100.millis).take(5) foreach (u => logger.info(u.toString))
  }
  "cpu load average" in {
    cpu.load(100.millis).take(5).foreach(l => logger.info(l.toString))
  }

}
