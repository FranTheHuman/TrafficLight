package application

import cats.Monad
import org.typelevel.log4cats.Logger
import domain.behavior.Finder
import domain.models.{Street, TrafficLights}

class FinderService[F[_]: Monad: Logger] extends Finder[F] {

  override def findTrafficLightsChanges(street: Street): F[List[TrafficLights]] = ???

  override def findStreetsWithYellowTL(): F[List[Street]] = ???

}
