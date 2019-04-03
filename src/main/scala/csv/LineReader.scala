/*
 * Copyright (c) 2019.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package csv
import java.io.{BufferedReader, Closeable, Reader}

import scala.annotation.tailrec
import scala.io.Source

/**
  * LineReader
  *
  * @author zhaihao
  * @version 1.0
  * @since 2019-04-02 17:57
  */
trait LineReader extends Closeable {
  def readLineWithTerminator(): String
}

class ReaderLineReader(reader: Reader) extends LineReader {

  private val bufferedReader: BufferedReader = new BufferedReader(reader)

  override def readLineWithTerminator() = {

    @tailrec
    def go(bufferedReader: BufferedReader, sb: StringBuilder): StringBuilder = {
      bufferedReader.read() match {
        case -1 => sb
        case c =>
          sb.append(c)
          c match {
            case '\n' | '\u2028' | '\u2029' | '\u0085' => sb
            case '\r' =>
              bufferedReader.mark(1)
              bufferedReader.read() match {
                case -1   => sb
                case '\n' => sb.append('\n')
                case _    => bufferedReader.reset(); sb
              }
            case _ => go(bufferedReader, sb.append(c.toChar))
          }
      }
    }

    val sb = go(bufferedReader, StringBuilder.newBuilder)
    if (sb.isEmpty) null else sb.toString
  }

  override def close() = {
    bufferedReader.close()
    reader.close()
  }
}

class SourceLineReader(source: Source) extends LineReader {
  override def readLineWithTerminator() = {

    @tailrec
    def go(source: Source, sb: StringBuilder): StringBuilder = {
      if (!source.hasNext) sb
      else {
        val c = source.next()
        sb.append(c)
        c match {
          case '\n' | '\u2028' | '\u2029' | '\u0085' => sb
          case '\r' =>
            if (!source.hasNext) sb
            else go(source, sb)
        }
      }
    }

    val sb = go(source, StringBuilder.newBuilder)
    if (sb.isEmpty) null else sb.toString
  }

  override def close() = source.close()
}
