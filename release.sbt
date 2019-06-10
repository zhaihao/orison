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
releaseTagComment           := s"chore: releasing ${(version in ThisBuild).value}"
releaseCommitMessage        := s"chore: prepare version ${(version in ThisBuild).value}"

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  releaseStepCommandAndRemaining("+publishSigned"),
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeReleaseAll")
)
