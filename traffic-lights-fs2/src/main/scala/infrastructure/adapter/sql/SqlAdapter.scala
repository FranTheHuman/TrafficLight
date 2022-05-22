package infrastructure.adapter.sql

import doobie.Read
import fs2.Stream

trait SqlAdapter[F[_]] {
  
  def executeQuery[T](query: String)(implicit r: Read[T]): Stream[F, T]

}
