/*
 * Copyright (c) 2020.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package console

import config.HConfig

import scala.util.Random

/** ProcessBarSpec
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2020/7/4
  *   22:15
  */
object ProcessBarSpec extends orison.App with HConfig {
  val its = 10

  val progress =
    ProgressBar(its,
                new BarFormatter(nCols = 100, unit = "samples") with OrdersOfMagnitudeScaling with UnicodeBarFormat
    )
  progress meter { updater =>
    (1 to its).foreach { _ =>
      Thread.sleep(Random.nextInt(1000))
      updater.update(1)
    }
  }

  //
  progress.start()
  (1 to its).foreach { _ =>
    Thread.sleep(Random.nextInt(1000))
    progress.update(1)
  }
  progress.stop()
}
