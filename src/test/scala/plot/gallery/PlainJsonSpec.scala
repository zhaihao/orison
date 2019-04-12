/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.gallery
import plot._
import test.BaseSpec

/**
  * PlainJsonSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-28 11:44
  */
class PlainJsonSpec extends BaseSpec {

  "json" ignore {

    // language=Json
    val json = """{
  "$schema": "https://vega.github.io/schema/vega-lite/v3.json",
  "data": {
    "url": ""
  },
  "transform": [
    {
      "filter": "datum.year == 2000"
    },
    {
      "calculate": "datum.sex == 2 ? 'Female' : 'Male'",
      "as": "gender"
    }
  ],
  "spacing": 10,
  "mark": "bar",
  "encoding": {
    "column": {
      "field": "age",
      "type": "ordinal"
    },
    "y": {
      "aggregate": "sum",
      "field": "people",
      "type": "quantitative",
      "axis": {
        "title": "population",
        "grid": false
      }
    },
    "x": {
      "field": "gender",
      "type": "nominal",
      "scale": {
        "rangeStep": 12
      },
      "axis": {
        "title": ""
      }
    },
    "color": {
      "field": "gender",
      "type": "nominal",
      "scale": {
        "range": [
          "#EA98D2",
          "#659CCA"
        ]
      }
    }
  },
  "config": {
    "view": {
      "stroke": "transparent"
    },
    "axis": {
      "domainWidth": 1
    }
  }
}"""

    plot.vega.data(url = DemoData.Population).json(json).html.browse
  }

}
