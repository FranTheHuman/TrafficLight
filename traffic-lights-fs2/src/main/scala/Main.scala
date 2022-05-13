import application.ReviewService
import cats.effect.kernel.Resource
import cats.effect.{Async, IO, IOApp, Sync}
import domain.models.TrafficLights
import domain.models.TrafficLights.*
import doobie.*
import doobie.hikari.*
import infrastructure.OutsideWorld.{ProducerImpl, RepositoryImpl}
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

  private def createReviewer[F[_]: Async](
    implicit dbConfig: DbConfiguration,
    httpConfig: HttpClientConfiguration
  ) = {

    implicit val logger: Logger[F] =
      Slf4jLogger.getLogger[F]

    val repository = new RepositoryImpl[F](dbConfig, httpConfig)
    val reviewer   = new ReviewService[F](repository)

    reviewer
  }

  implicit val dbConfig: DbConfiguration = DbConfiguration(
    "org.postgresql.Driver",
    "jdbc:postgresql://127.0.0.1:5432/streets",
    "root",
    "root",
  )

  implicit val httpConfig: HttpClientConfiguration = HttpClientConfiguration(
    uri"http://localhost:1080/reporting/v3/conversion-details/"
  )

  override def run: IO[Unit] =
    createReviewer[IO]
      .reviewTrafficLights()
      .evalMap(IO.println)
      .flatMap(_ => new ProducerImpl[IO]().produce())
      .handleErrorWith(error => Stream.eval(IO(println(error))))
      .compile
      .drain

}
