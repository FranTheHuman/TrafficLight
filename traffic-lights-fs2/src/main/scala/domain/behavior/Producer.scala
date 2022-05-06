package domain.behavior

import domain.models.Street

/**
 * Interface responsible for publishing status updates in kafka
 * @tparam F Effect
 */
trait Producer[F[_]] {
  def produce(): F[Unit]
}
