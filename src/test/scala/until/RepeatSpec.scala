/*
 * Copyright (c) 2020-2023.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package until

import com.typesafe.scalalogging.StrictLogging
import test.BaseSpec

/**
  * RepeatSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2023/1/17 15:36
  */
class RepeatSpec extends BaseSpec with StrictLogging{
  "repeat" in {
    util.repeat(3){
      logger.info("repeat test.")
    }
  }
}
