package infrastructure.adapter.http

case class HttpClientConfig(
  host: String,
  port: Option[Int],
  scheme: String
)
