import sbt._
object ghpages extends Build {
  override def projects = Seq(root)
  lazy val root = Project("sbt-ghpages-plugin", file(".")) dependsOn(git)
  // TODO: change back to joshes repo after a branch-key gets merged
  lazy val git = RootProject(uri("git://github.com/softprops/sbt-git-plugin.git#branch-key"))
}
