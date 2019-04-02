/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.dsl
import plot._
import plot.spec.Transform
import plot.spec.transform.{Calculate, Filter}

/**
  * TransformDSL
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-26 15:34
  */
trait TransformDSL {

  protected var transform: Option[Seq[Transform]] = None

  def calculate(calculate: String, as: String): this.type = {
    if (transform.isEmpty) this.transform = Seq.empty[Transform]

    transform = transform.map(_ :+ Calculate(calculate, as))

    this
  }

  def filter(filter: String): this.type = {
    if (transform.isEmpty) this.transform = Seq.empty[Transform]

    transform = transform.map(_ :+ Filter(filter))

    this
  }
}
