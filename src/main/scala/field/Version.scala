package field

/** Version
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2020/6/10
  *   3:25 下午
  */
import scala.util.control.Exception._

object Version {
  sealed trait Bump {
    def bump: Version => Version
  }

  object Bump {
    case object Major  extends Bump { def bump = _.bumpMajor  }
    case object Minor  extends Bump { def bump = _.bumpMinor  }
    case object Bugfix extends Bump { def bump = _.bumpBugfix }
    case object Nano   extends Bump { def bump = _.bumpNano   }
    case object Next   extends Bump { def bump = _.bump       }

    val default = Next
  }

  val VersionR             = """([0-9]+)((?:\.[0-9]+)+)?([.\-0-9a-zA-Z]*)?""".r
  val PreReleaseQualifierR = """[.-](?i:rc|m|alpha|beta)[.-]?[0-9]*""".r

  def apply(s: String): Option[Version] = {
    allCatch opt {
      val VersionR(maj, subs, qual) = s
      // parse the subVersions (if any) to a Seq[Int]
      val subSeq: Seq[Int] = Option(subs) map { str =>
        // split on . and remove empty strings
        str.split('.').filterNot(_.trim.isEmpty).map(_.toInt).toSeq
      } getOrElse Nil
      Version(maj.toInt, subSeq, Option(qual).filterNot(_.isEmpty))
    }
  }
}

case class Version(major: Int, subversions: Seq[Int], qualifier: Option[String]) extends Ordered[Version] {

  def bump = {
    val maybeBumpedPrerelease = qualifier.collect { case Version.PreReleaseQualifierR() =>
      withoutQualifier
    }
    def maybeBumpedLastSubversion = bumpSubversionOpt(subversions.length - 1)
    def bumpedMajor               = copy(major = major + 1)

    maybeBumpedPrerelease
      .orElse(maybeBumpedLastSubversion)
      .getOrElse(bumpedMajor)
  }

  def bumpMajor  = copy(major = major + 1, subversions = Seq.fill(subversions.length)(0))
  def bumpMinor  = maybeBumpSubversion(0)
  def bumpBugfix = maybeBumpSubversion(1)
  def bumpNano   = maybeBumpSubversion(2)

  def maybeBumpSubversion(idx: Int) = bumpSubversionOpt(idx) getOrElse this

  private def bumpSubversionOpt(idx: Int) = {
    val bumped = subversions.drop(idx)
    val reset  = bumped.drop(1).length
    bumped.headOption map { head =>
      val patch = (head + 1) +: Seq.fill(reset)(0)
      copy(subversions = subversions.patch(idx, patch, patch.length))
    }
  }

  def bump(bumpType: Version.Bump): Version = bumpType.bump(this)

  def withoutQualifier = copy(qualifier = None)
  def asSnapshot       = copy(qualifier = Some("-SNAPSHOT"))

  def string = "" + major + mkString(subversions) + qualifier.getOrElse("")

  private def mkString(parts: Seq[Int]) = parts.map("." + _).mkString

  override def compare(that: Version) = {
    val size = subversions.size
    require(size == that.subversions.size, "to compare version must have same pattern")
    require(qualifier.isEmpty || qualifier.get.toLowerCase() == "-SNAPSHOT", "to compare version with qualifier only support snapshot")

    if (major < that.major) -1
    else if (major > that.major) 1
    else {
      var i    = 0
      var flag = 0
      while (i < size && flag == 0) {
        if (subversions(i) < that.subversions(i)) flag = -1
        if (subversions(i) > that.subversions(i)) flag = 1
        i += 1
      }
      if (flag == 0) {
        if (qualifier.isDefined && that.qualifier.isEmpty) -1
        else if (qualifier.isEmpty && that.qualifier.isDefined) 1
        else 0
      } else flag
    }
  }
}
