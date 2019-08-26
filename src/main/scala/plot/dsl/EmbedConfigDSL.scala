/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.dsl

import plot.Theme
import plot.spec.{Themes, EmbedConfig}

/**
  * VegaConfigDSL
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-22 10:45
  */
trait EmbedConfigDSL {

  var embedConfig = EmbedConfig(Themes.Default)

  def theme(theme: Theme = Themes.Default): this.type = {
    embedConfig = embedConfig.copy(theme = theme)
    this
  }
}
