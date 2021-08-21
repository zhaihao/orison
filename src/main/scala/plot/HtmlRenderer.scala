/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package plot

import com.typesafe.scalalogging.StrictLogging

/** HtmlRenderer
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2019-03-22
  *   10:43
  */
/** HtmlRenderer
  *
  * @author
  *   zhaihao
  * @version 1.0
  * 2019-03-20 20:36
  */
class HtmlRenderer(vega: Vega) extends StrictLogging {

  val spec = vega.json

  logger.debug("\n" + spec)

  private val head = s"""|<head>
                         |    <meta charset="utf-8">
                         |    <title>scala plot</title>
                         |<script src="https://cdn.jsdelivr.net/npm/vega@${plot.VEGA_VERSION}"></script>
                         |<script src="https://cdn.jsdelivr.net/npm/vega-lite@${plot.VEGA_LITE_VERSION}"></script>
                         |<script src="https://cdn.jsdelivr.net/npm/vega-embed@${plot.VEGA_EMBED}"></script>
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
                         |            theme: '${vega.embedConfig.theme}', 
                         |            defaultStyle: true,
                         |            scaleFactor : 2
                         |          }
                         |        ).catch(console.warn);
                         |    </script>
                         |</body>
                         |""".stripMargin

  val page = s"""|<!DOCTYPE html>
                 |$head
                 |$body
                 |</html>
                 |""".stripMargin
}
