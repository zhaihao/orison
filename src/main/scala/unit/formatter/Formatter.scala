/*
 * Copyright (c) 2020-2024.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package unit.formatter

import squants.Quantity

/**
  * Formatter
  *
  * @author zhaihao
  * @version 1.0
  * @since 2024-12-09 17:38
  */
trait Formatter[A <: Quantity[A]] {
  def inBestUnit(quantity: Quantity[A]): A
}
