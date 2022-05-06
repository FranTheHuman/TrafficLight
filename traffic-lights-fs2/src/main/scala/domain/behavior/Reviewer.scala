package domain.behavior

import domain.models.*

/**
 * Interface responsible for reviewing the states, consulting the changes and return news
 * @tparam F Effect
 */
trait Reviewer[F[_]] {
  def reviewTrafficLights(streets: List[Street]): F[List[TrafficLights]]
}
