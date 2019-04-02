/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.dsl
import plot.{Title, _}

/**
  * TitleDSL
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-26 14:24
  */
trait TitleDSL {
  protected var title: Option[Title] = None

  def title(title: String): this.type = {
    this.title = Title(text = title)
    this
  }

  def title(title: Title): this.type = {
    this.title = title
    this
  }
}
