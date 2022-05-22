package infrastructure.adapter.sql

import cats.effect.*
import cats.effect.kernel.Resource
import cats.implicits.*
import cats.{Applicative, Functor, Monad, Monoid}
import domain.models.{Street, TrafficLights}
import doobie.*
import doobie.hikari.*
import doobie.implicits.*
import doobie.util.transactor.Transactor
import fs2.{Chunk, Pipe, Stream}
import infrastructure.adapter.http.HttpClientConfig
import infrastructure.adapter.sql.SqlAdapter
import infrastructure.models.configurations.DbConfiguration
import infrastructure.models.responses.ReportResponse
import io.circe.fs2.byteParser
import org.http4s.FormDataDecoder.formEntityDecoder
import org.http4s.Method.*
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.client.Client
import org.http4s.syntax.literals.uri
import org.http4s.{EntityDecoder, Header, Headers, HttpVersion, Request, Response, Uri}
import org.typelevel.ci.CIString
import org.typelevel.log4cats.Logger

import scala.concurrent.ExecutionContext

/**
 * Service in charge of searching for data in the outside world
 * @param dbConfig asd
 * @param M MonadCancel for Resource execution
 * @tparam F
 *   Effect
 */
class Repository[F[_]: Async: Applicative](
  dbConfig: DbConfiguration
 )(implicit M: MonadCancel[F, Throwable]) extends SqlAdapter[F] {

  private val transactor: Resource[F, HikariTransactor[F]] = HikariTransactor.newHikariTransactor[F](
    dbConfig.driver,
    dbConfig.url,
    dbConfig.user,
    dbConfig.pass,
    ExecutionContext.global // await connection here
  )

  override def executeQuery[T](query: String)(implicit r: Read[T]): Stream[F, T] =
    Stream
      .evalSeq(
        transactor
        .use(
          sql"select * from street;"
            .query[T]
            .to[List]
            .transact[F]
        )
      )

}
