import application.ReviewService
import cats.effect.kernel.Resource
import cats.effect.{Async, IO, IOApp, Sync}
import domain.models.TrafficLights
import domain.models.TrafficLights.*
import doobie.*
import doobie.hikari.*
import infrastructure.OutsideWorld.RepositoryImpl
import infrastructure.models.configurations.{DbConfiguration, HttpClientConfiguration}
import org.http4s.EntityDecoder
import org.http4s.blaze.client.*
import org.http4s.circe.jsonOf
import org.http4s.client.Client
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger
import fs2.Stream
import org.http4s.syntax.literals.uri

object Main extends IOApp.Simple {

  private def executeReview[F[_]: Async](
    dbConfig: DbConfiguration,
    httpConfig: HttpClientConfiguration
  ) = {

    implicit val logger: Logger[F] =
      Slf4jLogger.getLogger[F]

    val finder    = new RepositoryImpl[F](dbConfig, httpConfig)
    val reviewer  = new ReviewService[F](finder)

    reviewer
      .reviewTrafficLights()
      .handleErrorWith(error => Stream.eval(Sync[F].pure(println(error))))
      .evalMap(result => Sync[F].pure(println(result)))
      .compile
      .drain
  }

  val dbConfig: DbConfiguration = DbConfiguration(
    "org.postgresql.Driver",
    "jdbc:postgresql://127.0.0.1:5432/streets",
    "root",
    "root",
  )

  val httpConfig: HttpClientConfiguration = HttpClientConfiguration(
    uri"http://localhost:1080/reporting/v3/conversion-details/"
  )

  override def run: IO[Unit] =
    executeReview[IO](dbConfig, httpConfig)

}
