/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot.gallery.example.single_view
import plot._
import plot.gallery.DemoData
import plot.spec.encoding.{AggOp, Axis, Bin, Scale}
import plot.spec.{FieldType, Mark}
import test.BaseSpec

/**
  * BarChartsAndHistogramsSpec
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-26 16:48
  */
class BarChartsAndHistogramsSpec extends BaseSpec {
  "Simple Bar Chart" ignore {
    val data = Seq(
      Map("a" -> "A", "b" -> 28),
      Map("a" -> "B", "b" -> 55),
      Map("a" -> "C", "b" -> 43),
      Map("a" -> "D", "b" -> 91),
      Map("a" -> "E", "b" -> 81),
      Map("a" -> "F", "b" -> 53),
      Map("a" -> "G", "b" -> 19),
      Map("a" -> "H", "b" -> 87),
      Map("a" -> "I", "b" -> 52)
    )

    plot.vega
      .title("Simple Bar Chart")
      .data(values = data)
      .desc("A simple bar chart with embedded data.")
      .mark(Mark.Bar)
      .encodeX(field = "a", `type` = FieldType.Ordinal)
      .encodeY(field = "b", FieldType.Quantitative)
      .html
      .browse()
  }

  "Histogram" ignore {
    plot.vega
      .title("Histogram")
      .data(DemoData.Movies)
      .mark(Mark.Bar)
      .encodeX(bin = Bin(), field = "IMDB_Rating", `type` = FieldType.Quantitative)
      .encodeY(`type` = FieldType.Quantitative, aggregate = AggOp.Count)
      .html
      .browse()
  }

  "Histogram (from Binned Data)" ignore {
    val data = Seq(
      Map("bin_start" -> 8, "bin_end"  -> 10, "count" -> 7),
      Map("bin_start" -> 10, "bin_end" -> 12, "count" -> 29),
      Map("bin_start" -> 12, "bin_end" -> 14, "count" -> 71),
      Map("bin_start" -> 14, "bin_end" -> 16, "count" -> 127),
      Map("bin_start" -> 16, "bin_end" -> 18, "count" -> 94),
      Map("bin_start" -> 18, "bin_end" -> 20, "count" -> 54),
      Map("bin_start" -> 20, "bin_end" -> 22, "count" -> 17),
      Map("bin_start" -> 22, "bin_end" -> 24, "count" -> 5)
    )

    plot.vega
      .title(title = "Histogram (from Binned Data)")
      .data(values = data)
      .mark(Mark.Bar)
      .encodeX(field = "bin_start",
               bin = Bin(binned = true, step = 2),
               `type` = FieldType.Quantitative)
      .encodeX2(field = "bin_end")
      .encodeY(field = "count", `type` = FieldType.Quantitative)
      .html
      .browse()
  }

  "Aggregate Bar Chart" ignore {
    plot.vega
      .title("Aggregate Bar Chart")
      .data(url = DemoData.Population)
      .desc("A bar chart showing the US population distribution of age groups in 2000.")
      .filter("datum.year == 2000")
      .mark(Mark.Bar)
      .encodeY(field = "age", `type` = FieldType.Ordinal, scale = Scale(rangeStep = 17))
      .encodeX(field = "people",
               `type` = FieldType.Quantitative,
               aggregate = AggOp.Sum,
               axis = Axis(title = "population"))
      .html
      .browse()
  }

}
