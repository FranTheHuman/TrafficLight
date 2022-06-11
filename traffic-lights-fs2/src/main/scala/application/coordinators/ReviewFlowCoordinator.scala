package application.coordinators

import application.coordinators.ReviewFlowCoordinator
import application.handlers.ErrorHandler.reviewTlErrorHandler
import application.services.ReviewService
import cats.effect.IO.asyncForIO
import cats.effect.kernel.Resource
import cats.effect.{Async, IO, IOApp, Sync}
import cats.syntax.all.*
import cats.{Applicative, Monad, Show}
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

class ReviewFlowCoordinator[F[_]: Async: Logger] extends Coordinator[F] {

  override def coordName: String = "Review-Flow-Coordinator"

  override def coordinate(): F[Unit] =
    (for {
      _             <- sinfo(s"$coordName - Coordinating")
      trafficLights <- createReviewer[F](dbConfig, httpConfig).reviewTrafficLights()
      _             <- sinfo(s"$coordName - News: $trafficLights")
      producer = new Producer[F](producerConfig)
      producerResult <- produceTl(trafficLights, producer)
      _              <- sinfo(s"$coordName - Published News")
    } yield producerResult)
      .handleErrorWith(reviewTlErrorHandler(serror))
      .compile
      .drain

  private def createReviewer[F[_]: Async](
      dbConfig: DbConfiguration,
      httpConfig: HttpClientConfig
  )(implicit logger: Logger[F]): ReviewService[F] =
    ReviewService[F](
      new Repository[F](dbConfig),
      new HttpClient[F](httpConfig)
    )

  private def produceTl[F[_]: Async](
      tl: TrafficLights,
      p: Producer[F]
  )(implicit ser: Serializer[F, TrafficLights], s: Show[TrafficLights]): Stream[F, ProducerResult[Unit, String, TrafficLights]] =
    p.produceOne {
      Message("news", "traffic-light", tl)
    }

}
