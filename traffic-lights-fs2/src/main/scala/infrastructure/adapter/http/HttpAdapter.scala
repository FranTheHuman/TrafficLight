package infrastructure.adapter.http

import infrastructure.models.responses.ReportResponse
import org.http4s.{EntityDecoder, Header}

trait HttpAdapter[F[_]] {
  def GET[T](
    path: String,
    headers: List[Header.Raw] = List.empty[Header.Raw]
  )(implicit d: EntityDecoder[F, T]): F[T]
}
