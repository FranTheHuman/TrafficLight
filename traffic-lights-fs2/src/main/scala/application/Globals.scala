package application

import cats.{Applicative, Monad, Monoid}
import cats.effect.Async
import fs2.Stream

object Globals {

  extension[A](list: List[A]) {
    def toStream[F[_]: Applicative]: Stream[F, A] =
      Stream.emits(list).covary[F]
  }

}
