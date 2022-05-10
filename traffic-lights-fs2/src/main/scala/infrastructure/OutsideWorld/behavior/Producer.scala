package infrastructure.OutsideWorld.behavior

import fs2.Stream

/** Interface responsible for publishing status updates in kafka
  *
  * @tparam F
  *   Effect
  */
trait Producer[F[_]] {
  def produce(): Stream[F, Unit]
}
