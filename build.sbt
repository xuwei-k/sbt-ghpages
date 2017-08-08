lazy val root = (project in file("."))
  .settings(
    ScriptedPlugin.scriptedSettings,
    scriptedLaunchOpts += ("-Dplugin.version=" + version.value),
    scriptedLaunchOpts ++= sys.process.javaVmArguments.filter(
      a => Seq("-Xmx", "-Xms", "-XX", "-Dsbt.log.noformat").exists(a.startsWith)
    ),
    sbtPlugin := true,
    name := "sbt-ghpages",
    organization := "com.typesafe.sbt",
    version := "0.6.0-SNAPSHOT",
    resolvers += "jgit-repo" at "http://download.eclipse.org/jgit/maven",
    addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.8.5"),
    addSbtPlugin("com.typesafe.sbt" % "sbt-site" % "1.2.0")
  )
