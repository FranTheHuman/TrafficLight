package application

import cats.effect.{Async, IO, Sync}
import cats.syntax.all.*
import cats.{Applicative, Functor, Monad}
import domain.behavior.Reviewer
import domain.models.*
import fs2.{Pipe, Stream}
import org.typelevel.log4cats.Logger
import cats.implicits.*
import infrastructure.OutsideWorld.behavior.Repository

import scala.concurrent.ExecutionContext

class ReviewService[F[_]: Monad: Applicative: Functor](
   finder: Repository[F]
  )(implicit logger: Logger[F]) extends Reviewer[F] {

  override def reviewTrafficLights(): Stream[F, TrafficLights] =
    finder
      .findStreetsWithYellowTL()
      .map { street =>
        logger.info(s"$street")
        street
      }
      .through(finder.findReportChanges)
      .map { trafficLights =>
        logger.info(s"$trafficLights - Changed")
        trafficLights
      }
}
