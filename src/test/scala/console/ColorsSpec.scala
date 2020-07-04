/*
 * Copyright (c) 2020.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package console

import com.typesafe.scalalogging.StrictLogging
import test.BaseSpec

/**
  * ColorsSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2020/7/4 21:05
  */
class ColorsSpec extends BaseSpec with StrictLogging {

  "RB_RED" in { logger.info(Colors.RB_RED("RB_RED")) }
  "RB_ORANGE" in { logger.info(Colors.RB_ORANGE("RB_ORANGE")) }
  "RB_YELLOW" in { logger.info(Colors.RB_YELLOW("RB_YELLOW")) }
  "RB_GREEN" in { logger.info(Colors.RB_GREEN("RB_GREEN")) }
  "RB_BLUE" in { logger.info(Colors.RB_BLUE("RB_BLUE")) }
  "RB_INDIGO" in { logger.info(Colors.RB_INDIGO("RB_INDIGO")) }
  "RB_VIOLET" in { logger.info(Colors.RB_VIOLET("RB_VIOLET")) }

  "render" in {
    logger.info(Colors.render("\u001b[38;5;082m","hello color"))
  }
}