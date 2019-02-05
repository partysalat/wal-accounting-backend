lazy val Http4sVersion = "0.18.21"
lazy val CirceVersion = "0.10.1"
lazy val DoobieVersion = "0.5.3"
lazy val H2Version = "1.4.196"
lazy val LogbackVersion = "1.2.3"
lazy val ScalaTestVersion = "3.0.3"
lazy val ScalaCheckVersion = "1.13.4"
lazy val Log4CatsVersion = "0.1.1"
lazy val SimulacrumVersion = "0.14.0"
lazy val PureConfigVersion = "0.10.1"

enablePlugins(JavaAppPackaging)

lazy val root = (project in file("."))
  .settings(
    organization := "org.justkile",
    name := "wal-accounting-backend",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.4",
    scalacOptions := Seq(
      "-deprecation",
      "-encoding",
      "UTF-8",
      "-feature",
      "-language:existentials",
      "-language:implicitConversions",
      "-language:higherKinds",
      "-Ypartial-unification",
      "-Ywarn-unused:implicits",
      "-Ywarn-unused:imports",
      "-Ywarn-unused:locals",
      "-Ywarn-unused:params",
      "-Ywarn-value-discard"
    ),
    addCompilerPlugin("org.scalamacros" % "paradise"        % "2.1.0" cross CrossVersion.full),
    addCompilerPlugin("org.spire-math"  %% "kind-projector" % "0.9.6"),
    libraryDependencies ++= Seq(
      "org.http4s"            %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"            %% "http4s-circe"        % Http4sVersion,
      "org.http4s"            %% "http4s-dsl"          % Http4sVersion,
      "io.circe"              %% "circe-core"          % CirceVersion,
      "io.circe"              %% "circe-generic"       % CirceVersion,
      "io.circe"              %% "circe-parser"        % CirceVersion,
      "io.circe"              %% "circe-java8"        % CirceVersion,
      "com.h2database"        % "h2"                   % H2Version,
      "org.tpolecat"          %% "doobie-core"         % DoobieVersion,
      "org.tpolecat"          %% "doobie-h2"           % DoobieVersion,
      "org.tpolecat"          %% "doobie-scalatest"    % DoobieVersion,
      "ch.qos.logback"        % "logback-classic"      % LogbackVersion,
      "io.chrisdavenport"     %% "log4cats-slf4j"      % Log4CatsVersion,
      "com.github.pureconfig" %% "pureconfig"          % PureConfigVersion,
      "com.github.mpilquist"  %% "simulacrum"          % SimulacrumVersion,
      "org.scalatest"         %% "scalatest"           % ScalaTestVersion % Test,
      "org.scalacheck"        %% "scalacheck"          % ScalaCheckVersion % Test
    )
  )
