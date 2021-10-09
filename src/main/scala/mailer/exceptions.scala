/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package mailer

import scala.util.Try

/** exceptions
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2019-07-18
  *   15:46
  */
case class SendEmailException(email: Email, cause: Throwable)                            extends RuntimeException(cause)
case class SendEmailsException(email: Seq[Email], cause: Throwable)                      extends RuntimeException(cause)
case class TransportCloseException[T](result: Option[T], cause: Throwable)               extends RuntimeException(cause)
case class SendEmailTransportCloseException(result: Option[Try[Unit]], cause: Throwable) extends RuntimeException(cause)
case class SendEmailsTransportCloseException(results: Option[Seq[Try[Unit]]], cause: Throwable)
    extends RuntimeException(cause)
