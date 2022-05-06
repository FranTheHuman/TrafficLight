ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.2"

lazy val root = (project in file("."))
  .settings(
    name := "TrafficLight-fs2"
  )

val Fs2Version = "3.2.7"
val scalaTestVersion = "3.2.12"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
  "co.fs2" %% "fs2-core" % Fs2Version
)