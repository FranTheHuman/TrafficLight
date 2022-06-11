package application

import cats.effect.Async
import org.typelevel.log4cats.Logger
import fs2.Stream

object Globals {

  trait StreamLog[F[_]: Async: Logger] {

    def sinfo(msg: String): Stream[F, Unit] =
      Stream(Logger[F].info(msg))

    def serror(msg: String): Stream[F, Unit] =
      Stream(Logger[F].error(msg))

  }

}
