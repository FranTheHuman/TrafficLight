package application

import cats.effect.Async
import org.typelevel.log4cats.Logger
import fs2.Stream
import org.typelevel.log4cats.slf4j.Slf4jLogger

object Globals {

  trait StreamLog[F[_]: Async] {

    implicit val logger: Logger[F] = Slf4jLogger.getLogger[F]

    def sinfo(msg: String): Stream[F, Unit] =
      Stream(Logger[F].info(msg))

    def serror(msg: String): Stream[F, Unit] =
      Stream(Logger[F].error(msg))

  }

}
