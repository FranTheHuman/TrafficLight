package application

import cats.Applicative
import cats.effect.Async
import fs2.Stream

object Globals {

  extension[F[_]: Async, A](stream: Stream[F, List[A]]) {
    def removeList(): Stream[F, A] =
      stream
        .flatMap(list => Stream.evalSeq(Applicative[F].pure(list)))
  }

}
