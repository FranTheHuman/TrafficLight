import application.ReviewService
import cats.Show
import cats.effect.IO.asyncForIO
import cats.effect.kernel.Resource
import cats.effect.{Async, IO, IOApp, Sync}
import domain.models.TrafficLights
import domain.models.TrafficLights.*
import doobie.*
import doobie.hikari.*
import fs2.Stream
import fs2.kafka.{ProducerResult, Serializer}
import infrastructure.Configurations.*
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

    ReviewService[F](
      new Repository[F](dbConfig),
      new HttpClient[F](httpConfig)
    )
  }

  def produceTl[F[_]: Async](
    tl: TrafficLights,
    p: Producer[F]
  )(implicit ser: Serializer[F, T], s: Show[T]): Stream[F, ProducerResult[Unit, String, TrafficLights]] =
    p.produceOne {
      Message("news", "traffic-light", tl)
    }

  import TrafficLights.{showTl, trafficLightsSerializer}
  val producer = new Producer[IO](producerConfig)

  override def run: IO[Unit] =
    createReviewer[IO](dbConfig, httpConfig)
      .reviewTrafficLights()
      .flatMap(produceTl(_, producer))
      .handleErrorWith(error => Stream.eval(IO(println(error))))
      .compile
      .drain

}
