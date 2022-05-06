package application

import cats.effect.Sync
import domain.behavior.Reviewer
import domain.models.{Street, TrafficLights}

class ReviewService[F[_]: Sync] extends Reviewer[F] {

  override def reviewTrafficLights(streets: List[Street]): F[List[TrafficLights]] = ???

}
