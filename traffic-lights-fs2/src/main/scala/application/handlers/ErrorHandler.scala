package application.handlers

import application.Globals.StreamLog
import domain.models.TrafficLights
import fs2.Stream

object ErrorHandler {

  def reviewTlErrorHandler[F[_], A](log: String => Stream[F, Unit]): Throwable => Stream[F, A] = { error =>
    log(error.getMessage)
    error match {
      case _: Throwable => Stream.emits(List.empty[A]).covary[F]
    }
  }

}
