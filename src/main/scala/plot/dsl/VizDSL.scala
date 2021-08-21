/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.dsl

import os.ReadablePath
import plot._

/** JsonDSL
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2019-03-27
  *   17:58
  */
trait VizDSL { vega: Vega =>
  var viz: String = ""

  def viz(json: String): this.type = { viz = json; this }

  def viz(path: ReadablePath): this.type = {
    viz = os.read(path)

    this
  }
}
