import sbt._

object Dependencies {

  object V {
    val akka      = "2.6.19"
    val fs2       = "3.2.7"
    val scalaTest = "3.2.12"
  }

  val fs2             = "co.fs2"            %% "fs2-core"            % V.fs2
  val akkaStreams     = "com.typesafe.akka" %% "akka-stream"         % V.akka
  val akkaStreamsTest = "com.typesafe.akka" %% "akka-stream-testkit" % V.akka
  val scalatest       = "org.scalatest"     %% "scalatest"           % V.scalaTest
}
