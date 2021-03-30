import sun.misc.{Unsafe => JUnsafe}

import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox
import java.nio.ByteOrder
import scala.language.{existentials, postfixOps}
import scala.util.Try
import java.lang.Long.{reverseBytes => swap64}
import java.lang.Integer.{reverseBytes => swap32}
/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

/**
  * package
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-04-01 11:49
  */
package object unsafe {

  val unsafe: JUnsafe = Try {
    classOf[JUnsafe].getDeclaredFields
      .find { field =>
        field.getType == classOf[JUnsafe]
      }
      .map { field =>
        field.setAccessible(true)
        field.get(null).asInstanceOf[JUnsafe]
      }
      .getOrElse(throw new IllegalStateException("Can't find instance of sun.misc.Unsafe"))
  } recover {
    case th: Throwable => throw new ExceptionInInitializerError(th)
  } get

  def eval[A](expr: String) = {
    val toolbox = currentMirror.mkToolBox()
    val tree    = toolbox.parse(expr)
    toolbox.eval(tree).asInstanceOf[A]
  }

  private[this] final val isLittleEndian = ByteOrder.nativeOrder == ByteOrder.LITTLE_ENDIAN

  final val ByteArrayBase: Long = unsafe.arrayBaseOffset(Array[Byte]().getClass)

  final def getByte(input: Array[Byte], offset: Long): Byte =
    unsafe.getByte(input, offset)

  final def getInt(input: Array[Byte], offset: Long): Int =
    if (isLittleEndian) {
      unsafe.getInt(input, offset)
    } else {
      swap32(unsafe.getInt(input, offset))
    }

  final def getLong(input: Array[Byte], offset: Long): Long =
    if (isLittleEndian) {
      unsafe.getLong(input, offset)
    } else {
      swap64(unsafe.getLong(input, offset))
    }

  final def getUnsignedByte(input: Array[Byte], offset: Long): Int =
    unsafe.getByte(input, offset) & 0xFF

  final def getUnsignedInt(input: Array[Byte], offset: Long): Long =
    if (isLittleEndian) {
      unsafe.getInt(input, offset) & 0xFFFFFFFFL
    } else {
      swap32(unsafe.getInt(input, offset)) & 0xFFFFFFFFL
    }

  final def copyMemory(src: Array[Byte], srcOffset: Long, dest: Array[Byte], destOffset: Long, length: Int): Unit =
    unsafe.copyMemory(src, srcOffset, dest, destOffset, length)
}
