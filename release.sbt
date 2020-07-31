import sbt._
import sbtrelease.ReleaseStateTransformations._

import xerial.sbt.Sonatype._

// sonatype settings
useGpg                 := false
credentials            += Credentials(Path.userHome / ".sbt" / "sonatype_credential")
publishMavenStyle      := true
sonatypeProfileName    := "me.ooon"
licenses               := Seq("MPL2" -> url("http://mozilla.org/MPL/2.0/"))
sonatypeProjectHosting := Some(GitHubHosting("zhaihao", "orison", "zhaihao@ooon.me"))
publishTo              := sonatypePublishTo.value

releaseCrossBuild           := true
releaseIgnoreUntrackedFiles := true
releaseTagComment           := s"BUILD: releasing ${(version in ThisBuild).value}"
releaseCommitMessage        := s"BUILD: prepare version ${(version in ThisBuild).value}"

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("+publishSigned"),
  upgradeVersionBadge,
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeReleaseAll")
)

lazy val upgradeVersionBadge: ReleaseStep = ReleaseStep(
  action = { st: State =>
    val extracted = Project.extract(st)
    extracted.runTask(upgradeVersionBadgeTask, st)._1
  },
  enableCrossBuild = false
)
val upgradeVersionBadgeTask = taskKey[Unit]("upgrade the version badge")
upgradeVersionBadgeTask := {
  val log           = streams.value.log
  val versionString = (version in ThisBuild).value
  val file          = baseDirectory.value / "README.md"
  val originContent = IO.read(file)
  val newContent = originContent
    .replaceAll("""maven-v.*-519dd9\.svg""", s"maven-v$versionString-519dd9.svg")
    .replaceAll("""me\.ooon/orison_2\.12/.*""", s"me.ooon/orison_2.12/$versionString")
  IO.write(file, newContent)
  log.success(s"upgrade the version badge to $versionString")
}
