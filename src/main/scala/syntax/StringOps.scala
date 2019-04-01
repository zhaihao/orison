/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package syntax
import scala.language.implicitConversions

/**
  * StringOps
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-04-01 11:41
  */
final class StringOps private[syntax] (private val str: String) extends AnyVal {

  def toInt(radix: Int) = Integer.parseInt(str, radix)

  /**
    * 给 string 在头部补全长度
    * {{{
    *   "1".padStart(5,'0') ==> "00001"
    * }}}
    *
    * @param length 最小长度
    * @param c      补充的字符
    */
  def padStart(length: Int, c: Char) = {
    require(str != null, "string is null")
    if (str.length >= length) str
    else {
      var i = length - str.length
      val sb = new java.lang.StringBuilder
      while (i > 0) {
        sb.append(c)
        i -= 1
      }
      sb.append(str).toString
    }
  }

  /**
    * 给 string 在尾部补全长度
    *
    * {{{
    *   "1".padEnd(5,'0') ==> "10000"
    * }}}
    *
    * @param length 最小的长度
    * @param c      补充的字符
    */
  def padEnd(length: Int, c: Char) = {
    require(str != null, "string is null")
    if (str.length >= length) str
    else {
      var i = length - str.length
      val sb = new java.lang.StringBuilder
      sb.append(str)
      while (i > 0) {
        sb.append(c)
        i -= 1
      }
      sb.toString
    }
  }

  /**
    * string 重复若干次
    *
    * @param count 重复次数
    */
  def repeat(count: Int) = {
    require(str != null, "string is null")
    if (count < 0) throw new IllegalArgumentException("repeat times must > 0")
    else if (count == 0) ""
    else if (count == 1) str
    else {
      val len      = str.length
      val longSize = len.toLong * count.toLong
      val size     = longSize.toInt
      if (size != longSize)
        throw new ArrayIndexOutOfBoundsException("Required array size too large: " + longSize)

      val array = new Array[Char](size)
      str.getChars(0, len, array, 0)
      var n = len
      while (n < size - n) {
        System.arraycopy(array, 0, array, n, n)
        n <<= 1
      }
      System.arraycopy(array, 0, array, n, size - n)
      new String(array)
    }
  }

  /**
    * 后几位
    *
    * @param n 整数
    * @return
    */
  def subStringR(n: Int) = {
    require(n >= 0, "n must >= 0")

    val len = str.length
    if (len <= n) str
    else str.substring(len - n)
  }

  @inline def newline = str + "\n"
}

trait ToStringOps {
  @inline implicit def toStringOps(str: String) = new StringOps(str)
}
