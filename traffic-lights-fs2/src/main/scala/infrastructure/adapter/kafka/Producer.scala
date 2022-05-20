package infrastructure.adapter.kafka

import cats.effect.{Async, IO}
import cats.implicits.{catsSyntaxFlatten, toFlatMapOps, toTraverseOps}
import cats.syntax.all.{catsSyntaxFlatten, toFlatMapOps, toTraverseOps}
import cats.syntax.flatMap.{catsSyntaxFlatten, toFlatMapOps}
import cats.syntax.traverse.toTraverseOps
import cats.{Monad, Show}
import domain.models.TrafficLights
import fs2.Stream
import fs2.kafka.*
import infrastructure.adapter.kafka.ProducerAdapter
import infrastructure.adapter.kafka.models.{Message, ProducerConfig}

import scala.concurrent.duration.*

class Producer[F[_]: Async](producerConfig: ProducerConfig) extends ProducerAdapter[F] {

  private def producerSettings[T](implicit s: Serializer[F, T]): ProducerSettings[F, String, T] =
    ProducerSettings[F, String, T]
      .withBootstrapServers(producerConfig.boostrapServers)

  override def produceOne[T](
    message: Message[T]
  )(implicit ser: Serializer[F, T], s: Show[T]): Stream[F, ProducerResult[Unit, String, T]] =
    KafkaProducer
      .stream(producerSettings)
      .covary[F]
      .flatMap { producer =>
        Stream
          .emit {
            val record = ProducerRecord(message.topic, message.key, message.msg)
            ProducerRecords.one(record)
          }
          .evalMap { record =>
            producer.produce(record).flatten
          }
      }



}
