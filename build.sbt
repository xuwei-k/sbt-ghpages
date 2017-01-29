lazy val root = (project in file("."))
  .settings(
    sbtPlugin := true,
    name := "sbt-ghpages",
    organization := "com.typesafe.sbt",
    version := "0.6.0-SNAPSHOT",
    publishMavenStyle := false,
    publishTo <<= (version) { v =>
      def scalasbt(repo: String) = ("scalasbt " + repo, s"https://api.bintray.com/content/sbt/sbt-plugin-releases/sbt-ghpages/$v" )
      val (name, repo) = if (v.endsWith("-SNAPSHOT")) scalasbt("snapshots") else scalasbt("releases")
      Some(Resolver.url(name, url(repo))(Resolver.ivyStylePatterns))
    },
    resolvers += "jgit-repo" at "http://download.eclipse.org/jgit/maven",
    addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.8.5"),
    addSbtPlugin("com.typesafe.sbt" % "sbt-site" % "1.0.0")
  )
