/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.dsl
import plot.gallery.DemoData
import test.BaseSpec
import play.api.libs.json.Json
import plot.spec.{FieldType, Mark}
import plot._
import plot.spec.encoding.AggOp

import scala.language.existentials

/**
  * EncodingDSLSpec
  *
  * @author zhaihao
  * @version 1.0 2019-03-24 13:31
  */
class EncodingDSLSpec extends BaseSpec {

  "test encoding dsl" in {
    val v =
      plot.vega
        .data(url = DemoData.Cars)
        .mark(Mark.Area)
        .encodeX("a", FieldType.Nominal)
        .encodeY("b", FieldType.Ordinal)
    Json.json(v) ==> """|{
                        |  "$schema" : "https://vega.github.io/schema/vega-lite/v3.json",
                        |  "data" : {
                        |    "url" : "https://vega.github.io/editor/data/cars.json"
                        |  },
                        |  "mark" : {
                        |    "type" : "area"
                        |  },
                        |  "encoding" : {
                        |    "x" : {
                        |      "field" : "a",
                        |      "type" : "nominal"
                        |    },
                        |    "y" : {
                        |      "field" : "b",
                        |      "type" : "ordinal"
                        |    }
                        |  }
                        |}""".stripMargin
  }

  "test encoding with agg" in {
    val v = plot.vega.encodeX("a", FieldType.Quantitative, AggOp.Count)

    Json.json(v) ==> """|{
                        |  "$schema" : "https://vega.github.io/schema/vega-lite/v3.json",
                        |  "encoding" : {
                        |    "x" : {
                        |      "field" : "a",
                        |      "type" : "quantitative",
                        |      "aggregate" : "count"
                        |    }
                        |  }
                        |}""".stripMargin
  }

}
