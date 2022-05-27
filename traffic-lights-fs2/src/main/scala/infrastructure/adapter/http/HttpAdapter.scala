package infrastructure.adapter.http

import infrastructure.models.responses.ReportResponse
import org.http4s.{EntityDecoder, Header}
import org.typelevel.ci.CIString

trait HttpAdapter[F[_]] {
  def GET[T](
    path: String,
    headers: List[Header.Raw] = List(Header.Raw(CIString("Content-Type"), "application/json"))
  )(implicit d: EntityDecoder[F, T]): F[T]
}
