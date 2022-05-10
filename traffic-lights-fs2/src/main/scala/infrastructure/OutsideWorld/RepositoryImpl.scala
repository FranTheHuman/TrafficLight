package infrastructure.OutsideWorld

import cats.effect.*
import cats.effect.kernel.Resource
import cats.implicits.*
import cats.{Applicative, Functor, Monad}
import domain.models.{Street, TrafficLights}
import doobie.*
import doobie.hikari.*
import doobie.implicits.*
import doobie.util.transactor.Transactor
import fs2.{Chunk, Pipe, Stream}
import infrastructure.OutsideWorld.behavior.Repository
import infrastructure.models.configurations.{DbConfiguration, HttpClientConfiguration}
import infrastructure.models.responses.ReportResponse
import io.circe.fs2.byteParser
import org.http4s.FormDataDecoder.formEntityDecoder
import org.http4s.Method.*
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.client.Client
import org.http4s.syntax.literals.uri
import org.http4s.{EntityDecoder, Header, Headers, HttpVersion, Request, Response}
import org.typelevel.ci.CIString
import org.typelevel.log4cats.Logger

import scala.concurrent.ExecutionContext

/**
 * Service in charge of searching for data in the outside world
 * @param transactor Resource for database connection
 * @param httpClient Resource for http requests
 * @param M MonadCancel for Resource execution
 * @tparam F
 *   Effect
 */
class RepositoryImpl[F[_]: Async](
  dbConfig: DbConfiguration,
  httpConfig: HttpClientConfiguration
 )(implicit M: MonadCancel[F, Throwable]) extends Repository[F] {

  private val transactor: Resource[F, HikariTransactor[F]] = HikariTransactor.newHikariTransactor[F](
    dbConfig.driver,
    dbConfig.url,
    dbConfig.user,
    dbConfig.pass,
    ExecutionContext.global // await connection here
  )

  override def findReportChanges: Pipe[F, Street, TrafficLights] = ???
    // Stream
    //   .eval(
    //     BlazeClientBuilder[F]
    //       .resource
    //       .use(_.expect[ReportResponse](createRequest(street.id)))
    //   )
      // TODO: CONVERT TO TrafficLights

  lazy val createRequest: Int => Request[F] =
      streetId =>
        Request[F](
          GET,
          uri"${httpConfig.url}" / streetId,
          HttpVersion.`HTTP/2.0`,
          Headers(List(Header.Raw(CIString("Content-Type"), "application/json")))
        )

  override def findStreetsWithYellowTL(): Stream[F, Street] =
    Stream
       .eval(
         transactor
           .use(
             sql"Select * from street"
               .query[Street]
               .to[List]
               .transact[F]
           )
       )
       // TODO Transform Stream[F, List[Street] -> Stream[F, Street]

}
