package domain.behavior

import domain.models.*
import fs2.Stream

/**
 * Interface responsible for reviewing the states, consulting the changes and return news
 * @tparam F Effect
 */
trait Reviewer[F[_]] {
  def reviewTrafficLights(): Stream[F, TrafficLights]
}
