import application.coordinators.ReviewFlowCoordinator
import cats.effect.{IO, IOApp}

object Main extends IOApp.Simple:
  override def run: IO[Unit] = new ReviewFlowCoordinator[IO].coordinate()
