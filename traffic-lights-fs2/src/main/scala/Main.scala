import application.coordinators.ReviewFlowCoordinator
import cats.effect.{IO, IOApp}
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

object Main extends IOApp.Simple:
  override def run: IO[Unit] = new ReviewFlowCoordinator[IO].coordinate()
