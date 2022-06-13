import Dependencies._

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.2"

lazy val trafficLightsFs2 = (project in file("traffic-lights-fs2"))
  .settings(
    name := "TrafficLight-fs2",
    libraryDependencies ++= Seq(
      fs2,
      catsEffectTestingSpecs2 % Test,
      scalatest % Test,
      http4sDsl,
      http4sClient,
      http4sServer,
      http4sCirce,
      circeFs2,
      circeCore,
      circeGeneric,
      circeParse,
      //circeGenericExtras,
      kafkaFs2,
      doobieCore,
      doobieHickari,
      postgresDriver,
      log4catsCore,
      log4cats,
      logbackClassic % Runtime
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