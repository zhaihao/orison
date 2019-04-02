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
import plot._
import plot.spec.Mark

/**
  * DataDSLSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-22 11:23
  */
class DataDSLSpec extends BaseSpec {

  "data dsl url test" in {
    val v = plot.vega.data(url = DemoData.Cars).mark(Mark.Area)
    Json.json(v) ==> """|{
                        |  "$schema" : "https://vega.github.io/schema/vega-lite/v3.json",
                        |  "data" : {
                        |    "url" : "https://vega.github.io/editor/data/cars.json"
                        |  },
                        |  "mark" : {
                        |    "type" : "area"
                        |  }
                        |}""".stripMargin
  }

  "data dsl values test" in {
    val seq = Seq(
      Map("a" -> "C", "b" -> 2),
      Map("a" -> "C", "b" -> 7),
      Map("a" -> "C", "b" -> 4),
      Map("a" -> "D", "b" -> 1),
      Map("a" -> "D", "b" -> 2),
      Map("a" -> "D", "b" -> 6),
      Map("a" -> "E", "b" -> 8),
      Map("a" -> "E", "b" -> 4),
      Map("a" -> "E", "b" -> 7)
    )

    val v = plot.vega.data(values = seq).mark(Mark.Area)
    Json.json(v) ==> """|{
                        |  "$schema" : "https://vega.github.io/schema/vega-lite/v3.json",
                        |  "data" : {
                        |    "values" : [ {
                        |      "a" : "C",
                        |      "b" : 2
                        |    }, {
                        |      "a" : "C",
                        |      "b" : 7
                        |    }, {
                        |      "a" : "C",
                        |      "b" : 4
                        |    }, {
                        |      "a" : "D",
                        |      "b" : 1
                        |    }, {
                        |      "a" : "D",
                        |      "b" : 2
                        |    }, {
                        |      "a" : "D",
                        |      "b" : 6
                        |    }, {
                        |      "a" : "E",
                        |      "b" : 8
                        |    }, {
                        |      "a" : "E",
                        |      "b" : 4
                        |    }, {
                        |      "a" : "E",
                        |      "b" : 7
                        |    } ]
                        |  },
                        |  "mark" : {
                        |    "type" : "area"
                        |  }
                        |}""".stripMargin
  }
}
