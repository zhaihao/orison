import sbt.Keys.scalacOptions
// global
scalaVersion in Global := "2.12.11"
organization in Global := "me.ooon"

scalacOptions in Global ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings")
resolvers in Global += "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository"
externalResolvers in Global := Resolver.combineDefaultResolvers(resolvers.value.toVector,
                                                                mavenCentral = true)

excludeDependencies in Global ++= excludes
dependencyOverrides in Global ++= overrides

cancelable in Global := true
//

lazy val root = (project in file("."))
  .settings(
    moduleName          := "orison",
    name                := "orison",
    logBuffered in Test := false,
    libraryDependencies ++= Seq(log,java_mail).flatten,
    libraryDependencies ++= Seq(typesafe_config,
                                scalatest,
                                play_json,
                                os_lib,
                                json4s,
                                "org.scala-lang" % "scala-compiler" % scalaVersion.value),
    scalacOptions in (Compile, doc) ++= Seq(
      "-implicits",
      "-groups",
      "-doc-title",
      description.value,
      "-doc-version",
      scalaVersion.value,
      "-sourcepath",
      baseDirectory.in(LocalRootProject).value.getAbsolutePath,
      "-doc-source-url",
      scmInfo.value.get.browseUrl + "/tree/master€{FILE_PATH}.scala"
    )
  )

val ROOT = config("root")
lazy val docs = (project in file("docs"))
  .enablePlugins(SiteScaladocPlugin, ParadoxSitePlugin, ParadoxMaterialThemePlugin, GhpagesPlugin)
  .settings(
    moduleName := "docs",
    name       := "orison - Documents",
    ParadoxMaterialThemePlugin.paradoxMaterialThemeSettings(Paradox),
    previewLaunchBrowser := false,
    previewFixedPort     := Some(9000),
//    previewFixedIp       := Some("0.0.0.0"),
    ghpagesNoJekyll      := true,
    git.remoteRepo       := "git@github.com:zhaihao/orison.git",
    excludeFilter in ghpagesCleanSite := ((f: File) =>
      (ghpagesRepository.value / "CNAME").getCanonicalPath == f.getCanonicalPath),
    sourceDirectory in Paradox := sourceDirectory.value / "main" / "paradox",
    paradoxProperties in Paradox ++= Map(
      "scaladoc.base_url"   -> "http://orison.ooon.me/api/",
      "github.base_url"     -> "https://github.com/zhaihao/orison",
      "snip.build.base_dir" -> baseDirectory.value.getAbsolutePath,
      "snip.github_link"    -> "false"
    ),
    paradoxNavigationDepth in Paradox          := 3,
    sourceDirectory in Paradox in paradoxTheme := sourceDirectory.value / "main" / "paradox" / "_template",
    makeSite                                   := makeSite.dependsOn(paradox in Paradox).value,
    mappings in makeSite in Paradox ++= Seq(file("LICENSE") -> "LICENSE"),
    paradoxMaterialTheme in Paradox ~= {
      _.withColor("red", "teal")
        .withFavicon("assets/favicon.ico")
        .withCopyright("© zhaihao")
        .withRepository(uri("https://github.com/zhaihao/orison"))
        .withSocial(uri("https://github.com/zhaihao"),
                    uri("https://twitter.com/zhaihaoooon"),
                    uri("https://www.facebook.com/zhaihaome"))
        .withLanguage(java.util.Locale.CHINESE)
        .withCustomStylesheet("assets/custom.css")
    },
    autoAPIMappings := true,
    SiteScaladocPlugin
      .scaladocSettings(ROOT, mappings in (Compile, packageDoc) in root, "api/")
  )
