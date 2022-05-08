package application

import cats.Monad
import cats.effect.*
import cats.implicits.*
import domain.models.{Street, TrafficLights}
import doobie.*
import doobie.hikari.*
import doobie.implicits.*
import infrastructure.{DbConfiguration, Finder}
import org.typelevel.log4cats.Logger

class FinderService[F[_]](
  implicit transactor: Resource[F, HikariTransactor[F]],
  M: MonadCancel[F, Throwable]
 ) extends Finder[F] {

  override def findTrafficLightsChanges(street: Street): F[List[TrafficLights]] = ???


  override def findStreetsWithYellowTL(): F[List[Street]] =
    transactor
      .use {
        sql"Select * from street"
          .query[Street]
          .to[List]
          .transact[F]
      }



}
