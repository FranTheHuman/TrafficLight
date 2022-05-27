package infrastructure.adapter.http

import cats.effect.Async
import cats.effect.kernel.Resource
import com.sun.xml.internal.ws.transport.Headers
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.client.Client
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.syntax.literals.uri
import org.http4s.{EntityDecoder, Header, Method, Request, Uri}

class HttpClient[F[_]: Async](httpConfig: HttpClientConfig) extends HttpAdapter[F] {

  val resource: Resource[F, Client[F]] =
    BlazeClientBuilder[F].resource

  override def GET[T](
    path: String,
    headers: List[Header.Raw] = List.empty[Header.Raw]
  )(implicit d: EntityDecoder[F, T]): F[T] =
    resource
      .use { client =>
        client
          .expect[T] {
            Request[F]()
              .withMethod(Method.GET)
              .withUri(toUri(path))
              .withHeaders(headers)
          }
      }

  private def toUri(path: String): Uri =
    Uri
      .unsafeFromString(s"${httpConfig.scheme}.")

}
