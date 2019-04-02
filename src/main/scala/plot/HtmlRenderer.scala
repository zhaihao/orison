/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot

import com.typesafe.scalalogging.StrictLogging
import play.api.libs.json.Json

/**
  * HtmlRenderer
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-03-22 10:43
  */
/**
  * HtmlRenderer
  *
  * @author zhaihao
  * @version 1.0 2019-03-20 20:36
  */
class HtmlRenderer(vega: Vega) extends StrictLogging {

  val spec = vega.json.getOrElse(Json.json(vega))

  logger.debug("\n" + spec)

  private val style = """|<style>
                         |    #vg-tooltip-element table {
                         |      font-size: 10;
                         |    }
                         |</style>
                         |""".stripMargin

  private val head = s"""|<head>
                         |    <meta charset="utf-8">
                         |    <title>${vega.config.title}</title>
                         |    <script src="https://cdn.jsdelivr.net/npm/vega@$VEGA_VERSION"></script>
                         |    <script src="https://cdn.jsdelivr.net/npm/vega-lite@$VEGA_LITE_VERSION"></script>
                         |    <script src="https://cdn.jsdelivr.net/npm/vega-embed@$VEGA_EMBED"></script>
                         |</head>
                         |""".stripMargin

  private val longId = System.currentTimeMillis()
  // scaleFactor = 2 导出的图片会放大两倍，缩小 50% 在 高分屏上就会比较清晰
  private val body = s"""|<body style="text-align: center;">
                         |    <div id="viz$longId"></div>
                         |    <script type="text/javascript">
                         |        var specJson = $spec;
                         |        vegaEmbed(
                         |          '#viz$longId', 
                         |          specJson,
                         |        {
                         |            theme: '${vega.config.theme}', 
                         |            defaultStyle: true,
                         |            scaleFactor : 2
                         |          }
                         |        ).catch(console.warn);
                         |    </script>
                         |</body>
                         |""".stripMargin

  val page = s"""|<!DOCTYPE html>
                 |$head
                 |$style
                 |$body
                 |</html>
                 |""".stripMargin
}
