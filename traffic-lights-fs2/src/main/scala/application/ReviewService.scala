package application

import cats.effect.{Async, IO, Sync}
import cats.syntax.all.*
import cats.{Applicative, Functor, Monad}
import domain.behavior.Reviewer
import domain.models.*
import fs2.{Pipe, Stream}
import org.typelevel.log4cats.Logger
import cats.implicits.*
import infrastructure.adapter.http.{HttpClient, HttpClientAdapter}
import infrastructure.models.responses.ReportResponse
import infrastructure.repository.Repository

import scala.concurrent.ExecutionContext

class ReviewService[F[_]: Async](
   repository: Repository[F],
   http: HttpClientAdapter[F]
  )(implicit logger: Logger[F]) extends Reviewer[F] {

  override def reviewTrafficLights(): Stream[F, TrafficLights] =
    repository
      .findStreetsWithYellowTL()
      .through(findReportChanges)

  private def findReportChanges: Pipe[F, Street, TrafficLights] =
    (streetS: Stream[F, Street]) =>
      streetS
        .evalMap(getChanges)
        .flatMap(rr =>
          Stream
            .emits(rr.toTrafficLights)
            .covary[F]
        )

  private lazy val getChanges: Street => F[ReportResponse] =
    street =>
      http
        .get[ReportResponse](s"/${street.id}")

}
