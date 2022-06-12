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
import org.typelevel.log4cats.slf4j.Slf4jLogger

class ReviewFlowCoordinator[F[_]: Async] extends Coordinator[F] {

  override def coordName: String = "Review-Flow-Coordinator"

  override def coordinate(): F[Unit] =
    (for {
      _              <- sinfo(s"$coordName - Coordinating")
      repository      = new Repository[F](dbConfig)
      httpClient      = new HttpClient[F](httpConfig)
      trafficLights  <- ReviewService[F](repository, httpClient).reviewTrafficLights()
      _              <- sinfo(s"$coordName - News: $trafficLights")
      producer        = new Producer[F](producerConfig)
      producerResult <- producer.produceOne(Message("news", "traffic-light", trafficLights))
      _              <- sinfo(s"$coordName - Published News")
    } yield producerResult)
      .handleErrorWith(reviewTlErrorHandler(serror))
      .compile
      .drain

}
