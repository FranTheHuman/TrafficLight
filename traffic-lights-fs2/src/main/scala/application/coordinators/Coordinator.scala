package application.coordinators

import application.Globals.StreamLog
import cats.effect.IO
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

trait Coordinator[F[_]] extends StreamLog[F]:
  def coordName: String
  def coordinate(): F[Unit]
