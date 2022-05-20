package infrastructure.adapter.kafka.models

case class Message[T](
    topic: String,
    key: String,
    msg: T
)
