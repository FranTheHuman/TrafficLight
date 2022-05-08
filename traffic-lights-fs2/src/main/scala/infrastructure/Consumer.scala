package infrastructure

import fs2.Stream

/** Interface responsible for consuming status updates in kafka. Represents an external consumer.
  *
  * @tparam F
  *   Effect
  */
trait Consumer[F[_]] {
  def consume[A](): Stream[F, A]
}
