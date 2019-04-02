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
import plot.spec.Mark
import plot._

/**
 * MarkDSLSpec 
 *
 * @author zhaihao
 * @version 1.0 
 * @since 2019-03-22 15:12
 */
 class MarkDSLSpec extends BaseSpec{

  "mark dsl test" in {
   val v = plot.vega.data(DemoData.Cars).mark(Mark.Area)
   Json.prettyPrint(Json.toJson(v)) ==> """|{
                                           |  "$schema" : "https://vega.github.io/schema/vega-lite/v3.json",
                                           |  "data" : {
                                           |    "url" : "https://vega.github.io/editor/data/cars.json"
                                           |  },
                                           |  "mark" : {
                                           |    "type" : "area"
                                           |  }
                                           |}""".stripMargin
  }
}
