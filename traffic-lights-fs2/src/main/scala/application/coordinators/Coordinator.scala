package application.coordinators

import application.Globals.StreamLog

trait Coordinator[F[_]] extends StreamLog[F]:
  def coordName: String
  def coordinate(): F[Unit]
