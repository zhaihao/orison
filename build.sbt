import sbt.Keys.scalacOptions
scalaVersion := "2.13.6"
organization := "me.ooon"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings")
externalResolvers := Resolver.combineDefaultResolvers(resolvers.value.toVector, mavenCentral = true)

excludeDependencies ++= excludes
dependencyOverrides ++= overrides
updateConfiguration := updateConfiguration.value.withMissingOk(true)

cancelable := true
Compile / unmanagedJars ~= { uj =>
  Seq(Attributed.blank(file(System.getProperty("java.home").dropRight(3) + "lib/tools.jar"))) ++ uj
}
assembly / assemblyExcludedJars := ((assembly / fullClasspath) map { cp =>
  cp filter { _.data.getName == "tools.jar" }
}).value

moduleName         := "orison"
name               := "orison"
Test / logBuffered := false
libraryDependencies ++= Seq(log, java_mail, slick).flatten
libraryDependencies ++= Seq(typesafe_config,
                            scalatest,
                            squants,
                            play_json,
                            os_lib,
                            argon2,
                            json4s,
                            "org.scala-lang" % "scala-compiler" % scalaVersion.value
)
