/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package field

/** Idfa
  *
  * @author
  *   zhaihao
  * @version 1.0
  * 2017-12-27 19:04
  */
object Idfa {

  // language=RegExp
  val REGEX =
    """[0-9a-zA-Z]{8}([-][0-9a-zA-Z]{4}){4}[0-9a-zA-Z]{8}"""

  // 小写 8-4-4-4-12
  def normalize(idfa: Option[String]) = {
    idfa.flatMap(i => if (i.matches(REGEX)) idfa else None).map(_.toLowerCase)
  }
}
