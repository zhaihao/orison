/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package field

import scala.annotation.tailrec

/** Ip
  *
  * @author
  *   zhaihao
  * @version 1.0
  * 2017-12-27 19:19
  */
object Ip {

  /** 127.0.0.1 为正 128.0.0.1 为负 由于127是本地保留段，不会产生 start > end 的情况 */
  object v4 {

    // language=RegExp
    val REGEX =
      """((?:(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))\.){3}(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d))))"""

    def normalize(ip: Option[String]) =
      ip.flatMap(i => if (i.matches(REGEX)) ip else None)

    def toInt(s: String): Int =
      s.split('.')
        .zip(List(24, 16, 8, 0))
        .foldLeft(0)((b, si) => b + (si._1.toInt << si._2))

    def toString(a: Int): String =
      List((0xffffffff, 24), (0x00ffffff, 16), (0x0000ffff, 8), (0x000000ff, 0))
        .map(i => (a & i._1) >>> i._2)
        .mkString(".")

    def binarySearch(target: Int)(array: Array[(Int, Int)]) = {
      @tailrec
      def go(low: Int, high: Int): Option[Int] = low + (high - low) / 2 match {
        case _ if low > high               => None
        case mid if array(mid)._1 > target => go(low, mid - 1)
        case mid if array(mid)._2 < target => go(mid + 1, high)
        case mid                           => Some(mid)
      }

      go(0, array.length - 1)
    }
  }

  object v6 {}

}
