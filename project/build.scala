import sbt._
object ghpages extends Build {
  override def projects = Seq(root)
  lazy val root = Project("sbt-ghpages-plugin", file(".")) dependsOn(git)
  lazy val git = RootProject(uri("git://github.com/jsuereth/sbt-git-plugin.git"))
}
