/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.dsl

import plot.Theme
import plot.spec.{Themes, VegaConfig}

/**
  * VegaConfigDSL
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-22 10:45
  */
trait VegaConfigDSL {

  var config = VegaConfig("scala plot - scaviz", Themes.Default)

  def config(title: String = "scala plot - scaviz", theme: Theme = Themes.Default): this.type = {
    config = config.copy(title = title, theme = theme)
    this
  }
}
