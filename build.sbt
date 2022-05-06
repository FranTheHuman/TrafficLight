import Dependencies._

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.2"

lazy val trafficLightsFs2 = (project in file("traffic-lights-fs2"))
  .settings(
    name := "TrafficLight-fs2",
    libraryDependencies ++= Seq(
      fs2,
      scalatest % Test
    )
  )

lazy val trafficLightsAkkaStreams = (project in file("traffic-lights-akka-streams"))
  .settings(
    name := "TrafficLight-akkaStream",
    libraryDependencies ++= Seq(
      akkaStreams,
      akkaStreamsTest % Test
    )
  )