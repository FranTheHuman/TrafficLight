import sbt._

object Dependencies {

  object V {
    val akka      = "2.6.19"
    val fs2       = "3.2.7"
    val catsTest  = "1.4.0"
    val scalaTest = "3.2.12"
    val logback   = "1.2.11"
    val log4cats  = "2.3.1"
    val http4s    = "0.23.11"
    val doobie    = "1.0.0-RC1"
    val circe     = "0.14.0"
    val circe_2   = "0.15.0-M1"
    val kafkaFs2  = "2.5.0-M3"
  }

  val fs2                     = "co.fs2"            %% "fs2-core"                   % V.fs2
  val akkaStreams             = "com.typesafe.akka" %% "akka-stream"                % V.akka
  val akkaStreamsTest         = "com.typesafe.akka" %% "akka-stream-testkit"        % V.akka
  val scalatest               = "org.scalatest"     %% "scalatest"                  % V.scalaTest
  val catsEffectTestingSpecs2 = "org.typelevel"     %% "cats-effect-testing-specs2" % V.catsTest
  val log4catsCore            = "org.typelevel"     %% "log4cats-core"              % V.log4cats
  val log4cats                = "org.typelevel"     %% "log4cats-slf4j"             % V.log4cats
  val http4sDsl               = "org.http4s"        %% "http4s-dsl"                 % V.http4s
  val http4sServer            = "org.http4s"        %% "http4s-ember-server"        % V.http4s
  val http4sClient            = "org.http4s"        %% "http4s-blaze-client"        % V.http4s
  val http4sCirce             = "org.http4s"        %% "http4s-circe"               % V.http4s
  val doobieCore              = "org.tpolecat"      %% "doobie-core"                % V.doobie
  val doobieHickari           = "org.tpolecat"      %% "doobie-hikari"              % V.doobie
  val postgresDriver          = "org.tpolecat"      %% "doobie-postgres"            % V.doobie
  val logbackClassic          = "ch.qos.logback"     % "logback-classic"            % V.logback
  val circeGeneric            = "io.circe"          %% "circe-generic"              % V.circe
  val circeLiteral            = "io.circe"          %% "circe-literal"              % V.circe_2
  val circeFs2                = "io.circe"          %% "circe-fs2"                  % V.circe
  val kafkaFs2                = "com.github.fd4s"   %% "fs2-kafka"                  % V.kafkaFs2
}