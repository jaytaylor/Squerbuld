import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "SquerylDemo"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "org.squeryl" %% "squeryl" % "0.9.6.2-SNAPSHOT" withSources(),
      "postgresql" % "postgresql" % "8.4-701.jdbc4"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      resolvers ++= Seq("Scala.sh Releases" at "http://scala.sh/repositories/releases", "Scala.sh Snapshots" at "http://scala.sh/repositories/snapshots")      
    )

}
