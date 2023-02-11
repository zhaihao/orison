/*
 * Copyright (c) 2020-2023.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit <http://ooon.me> or mail to zhaihao@ooon.me
 */

package config

import scala.collection.mutable.{Map => MMap}

/**
  * Ini
  *
  * @author zhaihao
  * @version 1.0
  * @since 2023/1/18 18:49
  */

object Ini {
  private val ROOT = ""

  private def isComment(s: String) = s.startsWith(";") || s.startsWith("#")

  private def isSection(s: String) = s.startsWith("[")

  private def isBlank(s: String) = s.trim.isBlank

  private val keyAndValuePattern = """(.*)=(.*)""".r

  private def extractSelection(s: String) = s.replaceAll("^\\[", "").replaceAll("]$", "").trim

  private def extractParameter(s: String): (String, String) = {
    s.trim match {
      case keyAndValuePattern(key, value) => (key.trim, value.trim)
      case _                              => throw new Exception("ini file format error.")
    }
  }

  def parse(file: os.ReadablePath): Ini = {
    var selectionKey = ROOT
    val underlying = MMap(selectionKey -> MMap.empty[String, String])
    os.read
      .lines(file)
      .foreach(line => {
        if (isBlank(line) || isComment(line)) {
          // do nothing
        } else if (isSection(line)) {
          // new section
          selectionKey = extractSelection(line)
          underlying += (selectionKey -> MMap.empty[String, String])
        } else {
          underlying(selectionKey) += extractParameter(line)
        }
      })
    new Ini(underlying)
  }

  def parse(path: String): Ini = parse(os.Path(path))
}

class Ini(private val underlying: MMap[String, MMap[String, String]]) {
  def get(key: String, section: String) = underlying(section)(key)
  def get(key: String) = underlying(Ini.ROOT)(key)
}
