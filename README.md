# orison [![License][licenseImg]][licenseLink] [![TravisCI][travisCiImg]][travisCiLink] [![Codacy][codacyImg]][codacyLink] [![Version][versionImg]][versionLink]


自己的 scala lib。使用方式：

```scala
resolvers += "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository"
resolvers += Resolver.url("ooon ivy repo", url("https://repo.ooon.me/release"))(Resolver.ivyStylePatterns)

externalResolvers := Resolver.combineDefaultResolvers(resolvers.value.toVector, mavenCentral = true)
libraryDependencies += "me.ooon" %% "orison" % version
```

[licenseImg]: https://img.shields.io/badge/License-MPL%202.0-green.svg
[licenseLink]: LICENSE

[travisCiImg]: https://travis-ci.org/zhaihao/orison.svg?branch=master
[travisCiLink]: https://travis-ci.org/zhaihao/orison

[codacyImg]: https://api.codacy.com/project/badge/Grade/dd1dc5ec77f54cd8bb9032ba5f4bdef6
[codacyLink]: https://app.codacy.com/project/zhaihao/orison/dashboard

[versionImg]: https://img.shields.io/badge/ooon-v1.0.35-519dd9.svg
[versionLink]: https://repo.ooon.me/release/