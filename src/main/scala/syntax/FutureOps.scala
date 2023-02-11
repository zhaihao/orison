/*
 * Copyright (c) 2021.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package syntax

import scala.concurrent.duration.{Duration, DurationInt}
import scala.language.implicitConversions
import scala.concurrent.{Await, Future}

/** FutureOps
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2021/4/28
  *   18:16
  */
final class FutureOps[T] private[syntax] (private val f: Future[T]) extends AnyVal {
  def tryValued(implicit timeout: Duration) = Await.ready(f, timeout).value.get
  def valued(implicit timeout:    Duration) = Await.result(f, timeout)
}

trait ToFutureOps {
  @inline implicit def toFeatureOps[T](f: Future[T]) = new FutureOps(f)
}
