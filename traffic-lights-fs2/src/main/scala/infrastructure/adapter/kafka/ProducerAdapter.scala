package infrastructure.adapter.kafka

import cats.Show
import fs2.Stream
import fs2.kafka.{ ProducerResult, Serializer }
import infrastructure.adapter.kafka.models.Message

/** Interface responsible for publishing status updates in kafka
  *
  * @tparam F
  *   Effect
  */
trait ProducerAdapter[F[_]] {

  def produceOne[T](
      message: Message[T]
  )(implicit ser: Serializer[F, T], s: Show[T]): Stream[F, ProducerResult[Unit, String, T]]

}
