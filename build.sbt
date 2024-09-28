import sbt.Keys.scalacOptions
scalaVersion := "2.13.15"
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
libraryDependencies ++= Seq(log, java_mail, slick).flatten
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
  oshi,
  "org.scala-lang" % "scala-compiler" % scalaVersion.value % Provided
)
