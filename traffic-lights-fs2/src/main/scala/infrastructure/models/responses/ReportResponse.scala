package infrastructure.models.responses

import cats.effect.Async
import domain.models.TrafficLights
import io.circe.{Decoder, HCursor}
import org.http4s.EntityDecoder
import org.http4s.circe.jsonOf

case class ReportResponse(
  conversionDetails: List[ConversionDetails],
  street: Int,
  time: String
)

object ReportResponse {

  implicit val reportResponseDecoder: Decoder[ReportResponse] = new Decoder[ReportResponse] {
    final def apply(c: HCursor): Decoder.Result[ReportResponse] =
      for {
        conversionDetails <- c.downField("conversionDetails").as[List[ConversionDetails]]
        street <- c.downField("street").as[Int]
        time <- c.downField("time").as[String]
      } yield {
        new ReportResponse(conversionDetails, street, time)
      }
  }

  implicit def reportResponseDecoder[F[_]: Async]: EntityDecoder[F, ReportResponse] =
    jsonOf[F, ReportResponse]

  extension(rr: ReportResponse) {
    def toTrafficLights: List[TrafficLights] =
      rr
        .conversionDetails
        .map(cd => TrafficLights(cd.traffic_light, cd.status, Some(cd.changed_at), rr.street))
  }

}

case class ConversionDetails(
  traffic_light: Int,
  status: String,
  changed_at: String
)

object ConversionDetails {

  implicit val reportResponseDecoder: Decoder[ConversionDetails] = new Decoder[ConversionDetails] {
    final def apply(c: HCursor): Decoder.Result[ConversionDetails] =
      for {
        traffic_light <- c.downField("traffic_light").as[Int]
        status <- c.downField("status").as[String]
        changed_at <- c.downField("changed_at").as[String]
      } yield {
        new ConversionDetails(traffic_light, status, changed_at)
      }
  }

  /*

  implicit def reportResponseDecoder[F[_]: Async]: EntityDecoder[F, ReportResponse] =
    jsonOf[F, ReportResponse]

  implicit def seqReportResponseDecoder[F[_]: Async]: EntityDecoder[F, List[ReportResponse]] =
    jsonOf[F, List[ReportResponse]]
  */

}