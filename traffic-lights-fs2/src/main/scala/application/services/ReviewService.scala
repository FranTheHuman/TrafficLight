package application.services

import cats.effect.{ Async, IO, Sync }
import cats.implicits.*
import cats.syntax.all.*
import cats.{ Applicative, Functor, Monad }
import domain.behavior.Reviewer
import domain.models.*
import fs2.{ Pipe, Stream }
import infrastructure.adapter.http.{ HttpAdapter, HttpClient }
import infrastructure.adapter.sql.{ Repository, SqlAdapter }
import infrastructure.models.responses.ReportResponse
import infrastructure.statements.StreetStatements.*
import org.typelevel.log4cats.Logger

import scala.concurrent.ExecutionContext

class ReviewService[F[_]: Async](repository: SqlAdapter[F], http: HttpAdapter[F]) extends Reviewer[F] {

  override def reviewTrafficLights(): Stream[F, TrafficLights] =
    repository
      .executeQuery[Street](FIND_STREETS_WITH_YELLOWS_TL)
      .through(findReportChanges)

  private def findReportChanges: Pipe[F, Street, TrafficLights] =
    (streetS: Stream[F, Street]) =>
      streetS
        .evalMap(getChanges)
        .flatMap(toDomain)

  private lazy val getChanges: Street => F[ReportResponse] =
    street => http.GET[ReportResponse](s"reporting/v3/conversion-details/${street.id}")

  private lazy val toDomain: ReportResponse => Stream[F, TrafficLights] =
    rr =>
      Stream
        .emits(rr.toTrafficLights)
        .covary[F]

}
