/*
 * Copyright (c) 2020-2024.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package unit.groups

import scala.collection.immutable.{Set, SortedSet}

import squants.{Quantity, UnitOfMeasure}
/**
  * UnitGroup
  *
  * @author zhaihao
  * @version 1.0
  * @since 2024-12-09 17:40
  */
trait UnitGroup[A <: Quantity[A]] {

  def units: Set[UnitOfMeasure[A]]

  implicit val uomOrdering: Ordering[UnitOfMeasure[A]] = new UomOrdering[A]

  lazy val sortedUnits: SortedSet[UnitOfMeasure[A]] =
    SortedSet[UnitOfMeasure[A]]() ++ units
}

class UomOrdering[A <: Quantity[A]] extends Ordering[UnitOfMeasure[A]] {
  override def compare(x: UnitOfMeasure[A], y: UnitOfMeasure[A]): Int = {
    val siUnit = x(1).dimension.siUnit

    val xSI = x(1).to(siUnit)
    val ySI = y(1).to(siUnit)

    xSI.compare(ySI)
  }
}
