/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package csv

import java.io._

class CSVWriter(protected val writer: Writer)(implicit val format: CSVFormat) extends Closeable with Flushable {

  private[this] val printWriter: PrintWriter = new PrintWriter(writer)
  private[this] val quoteMinimalSpecs = Array('\r', '\n', format.quoteChar, format.delimiter)

  def close(): Unit = printWriter.close()

  def flush(): Unit = printWriter.flush()

  def writeAll(allLines: Seq[Seq[Any]]): Unit = {
    allLines.foreach(line => writeNext(line))
    if (printWriter.checkError) {
      throw new java.io.IOException("Failed to write")
    }
  }

  private def writeNext(fields: Seq[Any]): Unit = {

    def shouldQuote(field: String, quoting: Quoting): Boolean =
      quoting match {
        case QUOTE_ALL => true
        case QUOTE_MINIMAL =>
          var i = 0
          while (i < field.length) {
            val char = field(i)
            var j = 0
            while (j < quoteMinimalSpecs.length) {
              val quoteSpec = quoteMinimalSpecs(j)
              if (quoteSpec == char) {
                return true
              }
              j += 1
            }
            i += 1
          }
          false
        case QUOTE_NONE => false
        case QUOTE_NONNUMERIC =>
          var foundDot = false
          var i = 0
          while (i < field.length) {
            val char = field(i)
            if (char == '.') {
              if (foundDot) {
                return true
              } else {
                foundDot = true
              }
            } else if (char < '0' || char > '9') {
              return true
            }
            i += 1
          }
          false
      }

    def printField(field: String): Unit =
      if (shouldQuote(field, format.quoting)) {
        printWriter.print(format.quoteChar)
        var i = 0
        while (i < field.length) {
          val char = field(i)
          if (char == format.quoteChar || (format.quoting == QUOTE_NONE && char == format.delimiter)) {
            printWriter.print(format.quoteChar)
          }
          printWriter.print(char)
          i += 1
        }
        printWriter.print(format.quoteChar)
      } else {
        printWriter.print(field)
      }

    val iterator = fields.iterator
    var hasNext = iterator.hasNext
    while (hasNext) {
      val next = iterator.next()
      if (next != null) {
        printField(next.toString)
      }
      hasNext = iterator.hasNext
      if (hasNext) {
        printWriter.print(format.delimiter)
      }
    }

    printWriter.print(format.lineTerminator)
  }

  def writeRow(fields: Seq[Any]): Unit = {
    writeNext(fields)
    if (printWriter.checkError) {
      throw new java.io.IOException("Failed to write")
    }
  }
}

object CSVWriter {

  def open(file: File)(implicit format: CSVFormat): CSVWriter = open(file, false, "UTF-8")(format)

  def open(file: File, encoding: String)(implicit format: CSVFormat): CSVWriter = open(file, false, encoding)(format)

  def open(file: File, append: Boolean)(implicit format: CSVFormat): CSVWriter = open(file, append, "UTF-8")(format)

  def open(fos: OutputStream)(implicit format: CSVFormat): CSVWriter = open(fos, "UTF-8")(format)

  def open(file: String)(implicit format: CSVFormat): CSVWriter = open(file, false, "UTF-8")(format)

  def open(file: String, encoding: String)(implicit format: CSVFormat): CSVWriter = open(file, false, encoding)(format)

  def open(file: String, append: Boolean)(implicit format: CSVFormat): CSVWriter = open(file, append, "UTF-8")(format)

  def open(file: String, append: Boolean, encoding: String)(implicit format: CSVFormat): CSVWriter =
    open(new File(file), append, encoding)(format)

  def open(file: File, append: Boolean, encoding: String)(implicit format: CSVFormat): CSVWriter = {
    val fos = new FileOutputStream(file, append)
    open(fos, encoding)(format)
  }

  def open(fos: OutputStream, encoding: String)(implicit format: CSVFormat): CSVWriter = {
    try {
      val writer = new OutputStreamWriter(fos, encoding)
      open(writer)(format)
    } catch {
      case e: UnsupportedEncodingException => fos.close(); throw e
    }
  }

  def open(writer: Writer)(implicit format: CSVFormat): CSVWriter = {
    new CSVWriter(writer)(format)
  }
}
