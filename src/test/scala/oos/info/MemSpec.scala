/*
 * Copyright (c) 2020-2022.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package oos.info

import com.typesafe.scalalogging.StrictLogging
import oos.info
import test.BaseSpec

import scala.concurrent.duration.DurationInt

/**
  * MemSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2022/7/30 02:02
  */
class MemSpec extends BaseSpec with StrictLogging {
  "内存" in {
    logger.info(s"总内存：${mem.total}")
  }

  "内存使用率" in {
    mem.usage(100.millis).take(5).foreach(m => logger.info(m.toString))
  }
}
