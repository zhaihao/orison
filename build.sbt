import sbt.Keys.scalacOptions
scalaVersion := "3.3.4"
organization := "me.ooon"
target       := studioTarget.value
scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings")
externalResolvers := Resolver.combineDefaultResolvers(resolvers.value.toVector, mavenCentral = true)

excludeDependencies ++= excludes
dependencyOverrides ++= overrides
updateConfiguration := updateConfiguration.value.withMissingOk(true)

cancelable.withRank(KeyRanks.Invisible) := true

moduleName         := "orison"
name               := "orison"
Test / logBuffered := false
libraryDependencies ++= Seq(LOG, JAVA_MAIL, SLICK).flatten
libraryDependencies ++= Seq(
  typesafe_config,
  scalatest,
  scalatest_must,
  squants,
  play_json,
  os_lib,
  argon2,
  json4s,
  jbcrypt,
  oshi
)
