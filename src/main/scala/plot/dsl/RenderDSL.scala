/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.dsl

import plot.{HtmlRenderer, Vega}

/**
  * RenderDSL
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019/8/26 5:55 下午
  */
trait RenderDSL { this: Vega =>
  def html = new HtmlRenderer(this)
}

