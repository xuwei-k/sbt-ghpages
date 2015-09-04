sbtPlugin := true

name := "sbt-ghpages"

organization := "com.typesafe.sbt"

version := "0.5.5-SNAPSHOT"

publishMavenStyle := false

publishTo <<= (version) { v =>
  def scalasbt(repo: String) = ("scalasbt " + repo, s"https://api.bintray.com/content/sbt/sbt-plugin-releases/sbt-ghpages/$v" )
  val (name, repo) = if (v.endsWith("-SNAPSHOT")) scalasbt("snapshots") else scalasbt("releases")
  Some(Resolver.url(name, url(repo))(Resolver.ivyStylePatterns))
}
