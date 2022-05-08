package application

import cats.effect.{Async, IO, Sync}
import cats.syntax.all.*
import cats.{Applicative, Functor, Monad}
import domain.behavior.Reviewer
import domain.models.*
import fs2.Stream
import org.typelevel.log4cats.Logger
import cats.implicits.*
import infrastructure.Finder

import scala.concurrent.ExecutionContext

class ReviewService[F[_]: Monad: Logger](
   finder: Finder[F]
  ) extends Reviewer[F] {

  override def reviewTrafficLights(): Stream[F, TrafficLights] =
    Stream
      .evalSeq(reviewFlow)

  lazy val reviewFlow: F[List[TrafficLights]] =
    for {
      _             <- Logger[F].info("Finding Streets with Yellow Traffic Lights")
      streets       <- finder.findStreetsWithYellowTL()
      _             <- Logger[F].info("Finding Yellow Traffic Changes")
      tlChanged     <- streets.map(finder.findTrafficLightsChanges).sequence
      trafficLights = mergeTrafficLights(tlChanged)
    } yield trafficLights

  def mergeTrafficLights(ll: List[List[TrafficLights]]): List[TrafficLights] =
    ll.fold(List.empty[TrafficLights])(_ ::: _)

}
