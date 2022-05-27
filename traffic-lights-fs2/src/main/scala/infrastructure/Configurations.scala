package infrastructure

import infrastructure.adapter.http.HttpClientConfig
import infrastructure.adapter.kafka.models.ProducerConfig
import infrastructure.models.configurations.DbConfiguration

object Configurations {

  implicit val dbConfig: DbConfiguration = DbConfiguration(
    "org.postgresql.Driver",
    "jdbc:postgresql://127.0.0.1:5432/streets",
    "root",
    "root",
  )

  implicit val httpConfig: HttpClientConfig = HttpClientConfig(
    "localhost",
    1080,
    "http"
  )

  implicit val producerConfig: ProducerConfig = ProducerConfig(
    "localhost:9092"
  )

}
