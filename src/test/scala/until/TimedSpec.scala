/*
 * Copyright (c) 2020-2023.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package until

import com.typesafe.scalalogging.StrictLogging
import test.BaseSpec
import util.timed

/**
  * TimedSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2023/1/17 15:32
  */
class TimedSpec extends BaseSpec with StrictLogging{

  "timed" in {
    timed(logger,"timed"){
      Thread.sleep(10)
    }
  }
}
