package application

import cats.effect.{Async, IO, Sync}
import cats.implicits.*
import cats.syntax.all.*
import cats.{Applicative, Functor, Monad}
import domain.behavior.Reviewer
import domain.models.*
import fs2.{Pipe, Stream}
import infrastructure.adapter.http.{HttpClient, HttpClientAdapter}
import infrastructure.adapter.sql.{Repository, SqlAdapter}
import infrastructure.models.responses.ReportResponse
import org.typelevel.log4cats.Logger

import scala.concurrent.ExecutionContext

class ReviewService[F[_]: Async](
  repository: SqlAdapter[F],
  http: HttpClientAdapter[F]
  )(implicit logger: Logger[F]) extends Reviewer[F] {

  import Street.reader

  override def reviewTrafficLights(): Stream[F, TrafficLights] =
    repository
      .executeQuery[Street](query)
      .through(findReportChanges)

  private lazy val query: String = "SELECT * FROM street;"

  private def findReportChanges: Pipe[F, Street, TrafficLights] =
    (streetS: Stream[F, Street]) =>
      streetS
        .evalMap(getChanges)
        .flatMap(toDomain)

  private lazy val getChanges: Street => F[ReportResponse] =
    street =>
      http
        .get[ReportResponse](s"reporting/v3/conversion-details/${street.id}")

  private lazy val toDomain: ReportResponse => Stream[F, TrafficLights] =
    rr =>
      Stream
        .emits(rr.toTrafficLights)
        .covary[F]


}
