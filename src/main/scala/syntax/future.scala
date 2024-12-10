/*
 * Copyright (c) 2020-2024.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package syntax

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration

/**
  * future
  *
  * @author zhaihao
  * @version 1.0
  * @since 2024-12-10 13:35
  */
object future {
  extension [A](f: Future[A]) {
    def tryValued(implicit timeout: Duration) = Await.ready(f, timeout).value.get
    def valued(implicit timeout:    Duration) = Await.result(f, timeout)
  }
}
