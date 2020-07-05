import sbt.Keys.scalacOptions
import sbt.librarymanagement.UpdateConfiguration
// global
scalaVersion in Global := "2.12.11"
organization in Global := "me.ooon"

scalacOptions in Global ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings")
externalResolvers in Global := Resolver.combineDefaultResolvers(resolvers.value.toVector,
                                                                mavenCentral = true)

excludeDependencies in Global ++= excludes
dependencyOverrides in Global ++= overrides
updateConfiguration in Global := updateConfiguration.value.withMissingOk(true)

cancelable in Global := true
//

lazy val root = (project in file("."))
  .settings(
    moduleName          := "orison",
    name                := "orison",
    logBuffered in Test := false,
    libraryDependencies ++= Seq(log, java_mail).flatten,
    libraryDependencies ++= Seq(typesafe_config,
                                scalatest,
                                play_json,
                                os_lib,
                                argon2,
                                json4s,
                                "org.scala-lang" % "scala-compiler" % scalaVersion.value)
  )
