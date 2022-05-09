import sbt._

object Dependencies {

  object V {
    val akka      = "2.6.19"
    val fs2       = "3.2.7"
    val catsTest  = "1.4.0"
    val scalaTest = "3.2.12"
    val log4cats  = "2.3.0"
    val http4s    = "0.23.11"
    val doobie    = "1.0.0-RC1"
  }

  val fs2                     = "co.fs2"            %% "fs2-core"                   % V.fs2
  val akkaStreams             = "com.typesafe.akka" %% "akka-stream"                % V.akka
  val akkaStreamsTest         = "com.typesafe.akka" %% "akka-stream-testkit"        % V.akka
  val scalatest               = "org.scalatest"     %% "scalatest"                  % V.scalaTest
  val catsEffectTestingSpecs2 = "org.typelevel"     %% "cats-effect-testing-specs2" % V.catsTest
  val log4cats                = "org.typelevel"     %% "log4cats-slf4j"             % V.log4cats
  val http4sDsl               = "org.http4s"        %% "http4s-dsl"                 % V.http4s
  val http4sClient            = "org.http4s"        %% "http4s-blaze-client"        % V.http4s
  val doobieCore              = "org.tpolecat"      %% "doobie-core"                % V.doobie
  val doobieHickari           = "org.tpolecat"      %% "doobie-hikari"              % V.doobie
  val postgresDriver          = "org.tpolecat"      %% "doobie-postgres"            % V.doobie
}