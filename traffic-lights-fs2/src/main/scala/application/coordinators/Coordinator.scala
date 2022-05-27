package application.coordinators

trait Coordinator[F[_]]:
  def coordinate(): F[Unit]
