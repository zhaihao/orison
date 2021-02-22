import sbt.Keys.scalacOptions
scalaVersion := "2.12.13"
organization := "me.ooon"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings")
externalResolvers := Resolver.combineDefaultResolvers(resolvers.value.toVector, mavenCentral = true)

excludeDependencies ++= excludes
dependencyOverrides ++= overrides
updateConfiguration := updateConfiguration.value.withMissingOk(true)

cancelable := true
unmanagedJars in Compile ~= { uj =>
  Seq(Attributed.blank(file(System.getProperty("java.home").dropRight(3) + "lib/tools.jar"))) ++ uj
}
assemblyExcludedJars in assembly := ((fullClasspath in assembly) map { cp =>
  cp filter { _.data.getName == "tools.jar" }
}).value

moduleName          := "orison"
name                := "orison"
logBuffered in Test := false
libraryDependencies ++= Seq(log, java_mail).flatten
libraryDependencies ++= Seq(typesafe_config,
                            scalatest,
                            squants,
                            play_json,
                            os_lib,
                            argon2,
                            json4s,
                            "org.scala-lang" % "scala-compiler" % scalaVersion.value)
