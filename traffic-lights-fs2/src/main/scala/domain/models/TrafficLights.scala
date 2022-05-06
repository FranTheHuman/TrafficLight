package domain.models

case class TrafficLights(
  id: Int,
  status: Colour.type,
  changed_at: Option[String] = None,
  street_id: String
)