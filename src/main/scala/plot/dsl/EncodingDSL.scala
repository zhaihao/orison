/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.dsl
import plot.spec.encoding.{Axis, Bin, Legend, Scale}
import plot.spec.{Encode, Encoding}
import plot.{AggOp, FieldType, TimeUnit, _}

/**
  * EncodingDSL
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-22 17:24
  */
trait EncodingDSL {

  protected var encoding: Option[Encoding] = None

  def encodeX(field:     Option[String]    = None,
              `type`:    Option[FieldType] = None,
              aggregate: Option[AggOp]     = None,
              axis:      Option[Axis]      = None,
              bin:       Option[Bin]       = None,
              timeUnit:  Option[TimeUnit]  = None,
              scale:     Option[Scale]     = None): this.type = {
    if (encoding.isEmpty)
      encoding = Encoding(x = Encode(field, `type`, aggregate, axis, bin, timeUnit, scale))
    else
      encoding =
        encoding.map(_.copy(x = Encode(field, `type`, aggregate, axis, bin, timeUnit, scale)))

    this
  }

  def encodeX2(field:     Option[String]    = None,
               `type`:    Option[FieldType] = None,
               aggregate: Option[AggOp]     = None,
               axis:      Option[Axis]      = None,
               bin:       Option[Bin]       = None,
               timeUnit:  Option[TimeUnit]  = None): this.type = {
    if (encoding.isEmpty)
      encoding    = Encoding(x2 = Encode(field, `type`, aggregate, axis, bin, timeUnit))
    else encoding = encoding.map(_.copy(x2 = Encode(field, `type`, aggregate, axis, bin, timeUnit)))

    this
  }

  def encodeY(field:     Option[String]    = None,
              `type`:    Option[FieldType] = None,
              aggregate: Option[AggOp]     = None,
              axis:      Option[Axis]      = None,
              bin:       Option[Bin]       = None,
              timeUnit:  Option[TimeUnit]  = None,
              scale:     Option[Scale]     = None): this.type = {
    if (encoding.isEmpty)
      encoding = Encoding(y = Encode(field, `type`, aggregate, axis, bin, timeUnit, scale))
    else
      encoding =
        encoding.map(_.copy(y = Encode(field, `type`, aggregate, axis, bin, timeUnit, scale)))

    this
  }

  def encodeColor(field:  Option[String]    = None,
                  `type`: Option[FieldType] = None,
                  scale:  Option[Scale]     = None,
                  legend: Option[Legend]    = None): this.type = {
    if (encoding.isEmpty)
      encoding = Encoding(color = Encode(field, `type`, scale = scale, legend = legend))
    else
      encoding = encoding.map(_.copy(color = Encode(field, `type`, scale = scale, legend = legend)))

    this
  }

}
