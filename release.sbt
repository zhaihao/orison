import sbt._
import sbtrelease.ReleaseStateTransformations._

Global / onChangedBuildSource := ReloadOnSourceChanges
ThisBuild / versionScheme     := Some("early-semver")

publishConfiguration      := publishConfiguration.value.withOverwrite(true)
publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true)
githubTokenSource         := TokenSource.Environment("GITHUB_TOKEN")
githubOwner               := "zhaihao"
githubRepository          := "orison"
publishMavenStyle         := true
licenses                  := Seq("MPL2" -> url("https://www.mozilla.org/en-US/MPL/2.0/"))

releaseCrossBuild           := true
releaseIgnoreUntrackedFiles := true
releaseCommitMessage        := s"release: ${(ThisBuild / version).value}"
releaseNextCommitMessage    := s"prepare: ${(ThisBuild / version).value}"

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  setNextVersion,
  commitNextVersion,
  pushChanges
)
