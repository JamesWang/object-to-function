ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "object-to-function"
  )


val Http4sVersion = "1.0.0-M37"
libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-ember-server" % Http4sVersion,
  // "org.http4s" %% "http4s-client" % Http4sVersion,
  "org.http4s" %% "http4s-circe" % Http4sVersion,
  "org.http4s" %% "http4s-dsl" % Http4sVersion,
  "io.argonaut" %% "argonaut" % "6.3.9",
  "io.circe" %% "circe-generic" % "0.14.5",
  "org.slf4j" % "slf4j-api" % "2.0.5",
  "org.slf4j" % "slf4j-simple" % "2.0.5",
  "org.scala-lang" % "scala-reflect" % "2.13.10",
  "org.scalatest" %% "scalatest" % "3.3.0-SNAP4" % "test",
  "org.scalatest" %% "scalatest-flatspec" % "3.2.15" % Test
)
