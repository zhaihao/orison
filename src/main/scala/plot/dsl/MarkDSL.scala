/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.dsl
import plot.spec.Mark

/**
  * MarkDSL
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-22 14:51
  */
trait MarkDSL {
  protected var mark: Option[Mark] = None

  def mark(mark: Mark): this.type = {
    this.mark = mark
    this
  }

}
