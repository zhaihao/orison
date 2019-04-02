/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.spec

import test.BaseSpec
import plot.Vega

/**
  * VegaConfigSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-22 11:25
  */
class VegaConfigSpec extends BaseSpec {

  "vega config test" in {
    val s = Vega().config()
    s.config.toString ==> "VegaConfig(scala plot - scaviz,default)"
  }
}
