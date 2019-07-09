import sun.misc.{Unsafe => JUnsafe}

import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox
import java.io.File
import scala.language.{existentials, postfixOps}
import scala.util.Try
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
}
