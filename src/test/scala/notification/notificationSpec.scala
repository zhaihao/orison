/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package notification

import test.BaseSpec

/** notificationSpec
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2019-07-18
  *   14:51
  */
class notificationSpec extends BaseSpec {
  "osx" ignore {
    "message" in {
      import notification.osx._
      osx.notice(Notice(message = "hello world"))
    }

    "message with title" in {
      import notification.osx._
      osx.notice(Notice(message = "hello world", title = Some("title")))
    }

    "message with title with subtitle" in {
      import notification.osx._
      osx.notice(Notice(message = "hello world", title = Some("title"), subtitle = Some("subtitle")))
    }

    "message with title with sound" in {
      import notification.osx._
      osx.notice(Notice(message = "hello world", title = Some("title"), sound = Some(Sound.Glass)))
    }
  }
}
