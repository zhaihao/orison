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
  * @since 2019-07-18 14:36
  */
package object notification {

  object osx {

    object Sound extends Enumeration {
      type Sound = Value

      val Basso, Blow, Bottle, Frog, Funk, Glass, Hero, Morse, Ping, Pop, Purr, Sosumi, Submarine,
      Tink = Value
    }

    case class Notice(title:    Option[String] = None,
                      subtitle: Option[String] = None,
                      message:  String,
                      sound:    Option[Sound.Value] = None) {
      override def toString = {
        s"""display notification "$message"""" +
          title.map(t => s""" with title "$t"""").getOrElse("") +
          subtitle.map(t => s""" subtitle "$t"""").getOrElse("") +
          sound.map(t => s""" sound name "$t"""").getOrElse("")
      }
    }

    def notice(notice: Notice) = os.proc("osascript", "-e", notice.toString).call()
  }
}
