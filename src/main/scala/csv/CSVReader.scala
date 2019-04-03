/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package csv

import java.io._
import java.util.NoSuchElementException

import scala.io.Source

class CSVReader protected (private val lineReader: LineReader)(implicit format: CSVFormat) extends Closeable {

  private[this] val parser = new CSVParser(format)

  def readNext(): Option[List[String]] = {

    @scala.annotation.tailrec
    def parseNext(lineReader: LineReader, leftOver: Option[String] = None): Option[List[String]] = {

      val nextLine = lineReader.readLineWithTerminator()
      if (nextLine == null) {
        if (leftOver.isDefined) {
          throw new MalformedCSVException("Malformed Input!: " + leftOver)
        } else {
          None
        }
      } else {
        val line = leftOver.getOrElse("") + nextLine
        parser.parseLine(line) match {
          case None =>
            parseNext(lineReader, Some(line))
          case result => result
        }
      }
    }

    parseNext(lineReader)
  }

  def foreach(f: Seq[String] => Unit): Unit = iterator.foreach(f)

  def iterator: Iterator[Seq[String]] = new Iterator[Seq[String]] {

    private[this] var _next: Option[Seq[String]] = None

    def hasNext: Boolean = {
      _next match {
        case Some(_) => true
        case None => _next = readNext(); _next.isDefined
      }
    }

    def next(): Seq[String] = {
      _next match {
        case Some(row) =>
          val _row = row
          _next = None
          _row
        case None => readNext().getOrElse(throw new NoSuchElementException("next on empty iterator"))
      }
    }

  }

  def iteratorWithHeaders: Iterator[Map[String, String]] = {
    val headers = readNext()
    headers.map(headers => {
      iterator.map(line => headers.zip(line).toMap)
    }).getOrElse(Iterator())
  }

  def toStreamWithHeaders: Stream[Map[String, String]] = iteratorWithHeaders.toStream

  def toStream: Stream[List[String]] =
    Stream.continually(readNext()).takeWhile(_.isDefined).map(_.get)

  def all(): List[List[String]] = {
    toStream.toList
  }

  def allWithHeaders(): List[Map[String, String]] = {
    allWithOrderedHeaders()._2
  }

  def allWithOrderedHeaders(): (List[String], List[Map[String, String]]) = {
    val headers = readNext()
    val data = headers.map(headers => {
      val lines = all()
      lines.map(l => headers.zip(l).toMap)
    })
    (headers.getOrElse(Nil), data.getOrElse(Nil))
  }

  def close(): Unit = {
    lineReader.close()
  }
}

object CSVReader {

  val DEFAULT_ENCODING = "UTF-8"

  def open(source: Source)(implicit format: CSVFormat): CSVReader = new CSVReader(new SourceLineReader(source))(format)

  def open(reader: Reader)(implicit format: CSVFormat): CSVReader = new CSVReader(new ReaderLineReader(reader))(format)

  def open(file: File)(implicit format: CSVFormat): CSVReader = {
    open(file, this.DEFAULT_ENCODING)(format)
  }

  def open(file: File, encoding: String)(implicit format: CSVFormat): CSVReader = {
    val fin = new FileInputStream(file)
    try {
      open(new InputStreamReader(fin, encoding))(format)
    } catch {
      case e: UnsupportedEncodingException => fin.close(); throw e
    }
  }

  def open(filename: String)(implicit format: CSVFormat): CSVReader =
    open(new File(filename), this.DEFAULT_ENCODING)(format)

  def open(filename: String, encoding: String)(implicit format: CSVFormat): CSVReader =
    open(new File(filename), encoding)(format)

}
