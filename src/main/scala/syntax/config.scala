/*
 * Copyright (c) 2020-2024.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package syntax

import com.typesafe.config.{Config, ConfigList, ConfigMemorySize, ConfigObject, ConfigRenderOptions}

import scala.concurrent.duration.{Duration, FiniteDuration}
import scala.jdk.CollectionConverters.*

/**
  * config
  *
  * @author zhaihao
  * @version 1.0
  * @since 2024-12-10 11:38
  */
object config {
  extension (config: Config) {
    def getOrElse[T: Getter](path: String, default: => T): T =
      opt[T](path) getOrElse default

    def opt[T: Getter](path: String)(implicit getter: Getter[T]): Option[T] = {
      if (config.hasPathOrNull(path)) {
        Some(getter(config, path))
      } else None
    }

    def pretty: String = config.root().render(renderOpts)

    def json: String = config.root().render(renderJson)
  }

  type Getter[T] = (Config, String) => T

  case class Bytes(value: Long)

  private val renderOpts = ConfigRenderOptions
    .defaults()
    .setOriginComments(false)
    .setComments(false)
    .setJson(false)
    .setFormatted(true)

  private val renderJson = ConfigRenderOptions
    .defaults()
    .setOriginComments(false)
    .setComments(false)
    .setJson(true)
    .setFormatted(true)

  implicit val stringGetter:         Getter[String]           = _ getString _
  implicit val booleanGetter:        Getter[Boolean]          = _ getBoolean _
  implicit val intGetter:            Getter[Int]              = _ getInt _
  implicit val doubleGetter:         Getter[Double]           = _ getDouble _
  implicit val longGetter:           Getter[Long]             = _ getLong _
  implicit val bytesGetter:          Getter[Bytes]            = (c, p) => Bytes(c getBytes p)
  implicit val durationGetter:       Getter[Duration]         = (c, p) => Duration.fromNanos((c getDuration p).toNanos)
  implicit val finiteDurationGetter: Getter[FiniteDuration]   = (c, p) => Duration.fromNanos((c getDuration p).toNanos)
  implicit val configListGetter:     Getter[ConfigList]       = _ getList _
  implicit val configGetter:         Getter[Config]           = _ getConfig _
  implicit val objectGetter:         Getter[ConfigObject]     = _ getObject _
  implicit val memorySizeGetter:     Getter[ConfigMemorySize] = _ getMemorySize _
  implicit val listConfigGetter:     Getter[List[Config]]     = (c, s) => c.getConfigList(s).asScala.toList
  implicit val listStringGetter:     Getter[List[String]]     = (c, s) => c.getStringList(s).asScala.toList
  implicit val listIntGetter:        Getter[List[Int]]        = (c, s) => c.getIntList(s).asScala.toList.map(_.intValue())
  implicit val listDoubleGetter:     Getter[List[Double]]     = (c, s) => c.getDoubleList(s).asScala.toList.map(_.doubleValue())
  implicit val listBooleanGetter:    Getter[List[Boolean]]    = (c, s) => c.getBooleanList(s).asScala.toList.map(_.booleanValue())
  implicit val listLongGetter:       Getter[List[Long]]       = (c, s) => c.getLongList(s).asScala.toList.map(_.longValue())
}
