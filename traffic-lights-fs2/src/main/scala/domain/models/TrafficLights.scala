package domain.models

import cats.Show
import cats.effect.Async
import fs2.kafka.Serializer
import io.circe.{Decoder, HCursor}
import org.http4s.FormDataDecoder.field
import org.http4s.circe.jsonOf
import org.http4s.{EntityDecoder, FormDataDecoder}

case class TrafficLights(
  id: Int,
  status: String,
  changed_at: Option[String] = None,
  street_id: Int
)

object TrafficLights {

  implicit val trafficLightsDecoder: Decoder[TrafficLights] = new Decoder[TrafficLights] {
    final def apply(c: HCursor): Decoder.Result[TrafficLights] =
      for {
        id <- c.downField("id").as[Int]
        status <- c.downField("status").as[String]
        changed_at <- c.downField("changed_at").as[Option[String]]
        street_id <- c.downField("street_id").as[Int]
      } yield {
        new TrafficLights(id, status, changed_at, street_id)
      }
  }

  implicit def trafficLightsDecoder[F[_]: Async]: EntityDecoder[F, TrafficLights] =
    jsonOf[F, TrafficLights]

  implicit def seqTrafficLightsDecoder[F[_]: Async]: EntityDecoder[F, List[TrafficLights]] =
    jsonOf[F, List[TrafficLights]]

  implicit def trafficLightsSerializer[F[_]: Async]: Serializer[F, TrafficLights] =
    Serializer.lift[F, TrafficLights](s => Async[F].pure(s.toString.getBytes("UTF-8")))

  implicit val showTl: Show[TrafficLights] = Show.fromToString
  
}