/*
 * Copyright (c) 2020-2023.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package pb

import com.typesafe.scalalogging.StrictLogging
import test.BaseSpec

/**
  * BarSpec
  * 暂不支持 idea test
  *
  * @author zhaihao
  * @version 1.0
  * @since 2023/1/4 22:52
  */
object BarSpec extends StrictLogging{
  def main(args: Array[String]): Unit = {
    val its = 100
    val progress = Bar(its)
    progress meter { updater =>
      (1 to its).foreach { i =>
        Thread.sleep(100)
        updater.update()
      }
    }
  }
}
