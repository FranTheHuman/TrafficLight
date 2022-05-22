import application.ReviewService
import cats.effect.IO.asyncForIO
import cats.effect.kernel.Resource
import cats.effect.{Async, IO, IOApp, Sync}
import domain.models.TrafficLights
import domain.models.TrafficLights.*
import doobie.*
import doobie.hikari.*
import fs2.Stream
import fs2.kafka.ProducerResult
import infrastructure.adapter.http.{HttpClient, HttpClientConfig}
import infrastructure.adapter.kafka.Producer
import infrastructure.adapter.kafka.models.{Message, ProducerConfig}
import infrastructure.adapter.sql.Repository
import infrastructure.models.configurations.DbConfiguration
import org.http4s.EntityDecoder
import org.http4s.blaze.client.*
import org.http4s.circe.jsonOf
import org.http4s.client.Client
import org.http4s.syntax.literals.uri
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

object Main extends IOApp.Simple {

  def createReviewer[F[_]: Async](
    dbConfig: DbConfiguration,
    httpConfig: HttpClientConfig
  ): ReviewService[F] = {

    implicit val logger: Logger[F] =
      Slf4jLogger.getLogger[F]

    ReviewService[F](new Repository[F](dbConfig), new HttpClient[F](httpConfig))
  }

  def produceTl[F[_]: Async](tl: TrafficLights)(implicit producerConfig: ProducerConfig): Stream[F, ProducerResult[Unit, String, TrafficLights]] = {
    import TrafficLights.{showTl, trafficLightsSerializer}
    new Producer[F](producerConfig).produceOne(Message("news", "traffic-light", tl)) // TODO: Not create producer here
  }

  implicit val dbConfig: DbConfiguration = DbConfiguration(
    "org.postgresql.Driver",
    "jdbc:postgresql://127.0.0.1:5432/streets",
    "root",
    "root",
  )

  implicit val httpConfig: HttpClientConfig = HttpClientConfig(
    "localhost",
    Some(1080),
    "reporting/v3/conversion-details"
  )

  implicit val producerConfig: ProducerConfig = ProducerConfig(
    "localhost:9092"
  )

  override def run: IO[Unit] =
    createReviewer[IO](dbConfig, httpConfig)
      .reviewTrafficLights()
      .flatMap(produceTl)
      .handleErrorWith(error => Stream.eval(IO(println(error))))
      .compile
      .drain

}
