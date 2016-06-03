import cn.avalon.json._
import spray.revolver.RevolverPlugin._

name := "Hakka Shopping"

scalaVersion := "2.10.5"

crossScalaVersions := Seq(scalaVersion.value)

organization := "cn.kakka"

versionWithGit

git.baseVersion := "0.2"

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io"
)

libraryDependencies ++= {
  val akkaVersion       = "2.3.13"
  val akkaStreamVersion = "1.0"
  val sprayVersion      = "1.3.3"
  val kamonVersion      = "0.4.0"
  Seq(
    "com.typesafe.akka" %% "akka-actor"   % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j"   % akkaVersion,
    "com.typesafe.akka" %% "akka-persistence-experimental" % akkaVersion,
    "com.typesafe.akka" %% "akka-multi-node-testkit"      % akkaVersion   % "test",
    "com.typesafe.akka" %% "akka-testkit"                 % akkaVersion   % "test",
    "com.typesafe.akka" %% "akka-stream-experimental" % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-core-experimental" % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-stream-testkit-experimental" % akkaStreamVersion % "test",
    "io.spray"          %% "spray-can"       % sprayVersion,
    "io.spray"          %% "spray-routing"   % sprayVersion,
    "io.spray"          %% "spray-json"      % "1.3.2",
    "io.spray" %% "spray-testkit" % sprayVersion % "test",
    "org.scalatest"     %% "scalatest"       % "2.2.4"       % "test",
    "io.kamon" %% "kamon-core" % kamonVersion,
    "io.kamon" %% "kamon-akka" % kamonVersion,
    "io.kamon" %% "kamon-datadog" % kamonVersion,
    "io.kamon" %% "kamon-system-metrics" % kamonVersion,
    "io.kamon" % "sigar-loader" % "1.6.6",
    "org.aspectj" % "aspectjweaver" % "1.8.5",
    "ch.qos.logback"  %  "logback-classic"    % "1.1.3",
    "commons-io" % "commons-io" % "1.3.2" % "test",
    "commons-codec" % "commons-codec" % "1.9",
    "org.reactivemongo" %% "reactivemongo" % "0.11.11"
  )
}

scalacOptions in ThisBuild ++= Seq("-feature", "-deprecation", "-unchecked", "-encoding", "UTF-8", "-language:postfixOps", "-nobootcp")

mainClass in Compile := Some("kakka.BootApi")

javaOptions ++= Seq(
  "-Dhttp.port=8080",
  "-Djvm-debug 5005",
  "-Dkamon.statsd.hostname=172.17.8.101",
  "-Dakka.persistence.journal.leveldb.native=off"
)

Revolver.settings
