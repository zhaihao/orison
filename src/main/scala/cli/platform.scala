/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package cli
import java.net.{URL, UnknownHostException}

/**
  * platform
  *
  * @author zhaihao
  * @version 1.0 2018/10/30 11:05
  */
private[cli] object platform {
  val _NL = System.getProperty("line.separator")

  import java.io.File
  import java.net.{InetAddress, URI}
  import java.text.SimpleDateFormat
  import java.util.{Calendar, GregorianCalendar, Locale}

  type ParseException = java.text.ParseException
  def mkParseEx(s: String, p: Int) = new java.text.ParseException(s, p)

  trait PlatformReadInstances {
    def calendarRead(pattern: String): Read[Calendar] = calendarRead(pattern, Locale.getDefault)

    def calendarRead(pattern: String, locale: Locale): Read[Calendar] =
      Read.reads { s =>
        val fmt = new SimpleDateFormat(pattern)
        val c   = new GregorianCalendar
        c.setTime(fmt.parse(s))
        c
      }

    implicit val yyyymmdddRead: Read[Calendar]    = calendarRead("yyyy-MM-dd")
    implicit val fileRead:      Read[File]        = Read.reads { new File(_) }
    implicit val inetAddress:   Read[InetAddress] = Read.reads { InetAddress.getByName }
    implicit val uriRead:       Read[URI]         = Read.reads { new URI(_) }
    implicit val urlRead:       Read[URL]         = Read.reads { new URL(_) }
  }

  def applyArgumentExHandler[C](desc: String,
                                arg:  String): PartialFunction[Throwable, Either[Seq[String], C]] = {
    case _: NumberFormatException =>
      Left(Seq(desc + " expects a number but was given '" + arg + "'"))
    case _: UnknownHostException =>
      Left(Seq(
        desc + " expects a host name or an IP address but was given '" + arg + "' which is invalid"))
    case _: ParseException =>
      Left(Seq(desc + " expects a Scala duration but was given '" + arg + "'"))
    case e: Throwable => Left(Seq(desc + " failed when given '" + arg + "'. " + e.getMessage))
  }
}
