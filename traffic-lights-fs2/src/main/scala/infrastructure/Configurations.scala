package infrastructure

import infrastructure.adapter.http.HttpClientConfig
import infrastructure.adapter.kafka.models.ProducerConfig
import infrastructure.models.configurations.DbConfiguration

object Configurations {

  val dbConfig: DbConfiguration = DbConfiguration(
    "org.postgresql.Driver",
    "jdbc:postgresql://127.0.0.1:5432/streets",
    "root",
    "root",
  )

  val httpConfig: HttpClientConfig = HttpClientConfig(
    "localhost",
    1080,
    "http"
  )

  val producerConfig: ProducerConfig = ProducerConfig(
    "localhost:9092"
  )

}
