package domain.behavior

/**
 * Interface responsible for consuming status updates in kafka.
 * Represents an external consumer.
 * @tparam F Effect
 */
trait Consumer[F[_]] {
  def produce(): F[Unit]
}
