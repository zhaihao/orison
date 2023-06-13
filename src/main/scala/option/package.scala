import scala.language.implicitConversions
/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

/** package
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2019-04-01
  *   14:49
  */
package object option {
  implicit def anyToOption[T](t: T): Option[T] = Option(t)
}
