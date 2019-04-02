/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.dsl
import plot._

/**
  * DescriptionDSL
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-26 16:52
  */
trait DescriptionDSL {
  protected var description: Option[String] = None

  def desc(desc: String): this.type = {
    this.description = desc
    this
  }
}
