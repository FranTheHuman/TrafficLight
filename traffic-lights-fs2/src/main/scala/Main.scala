import application.{FinderService, ReviewService}
import cats.effect.{Async, IO, IOApp}
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger


object Main extends IOApp.Simple {
  
  def executeReview[F[_]: Async]() = {

    implicit val logger: Logger[F] =
      Slf4jLogger.getLogger[F]

    val finder = new FinderService[F]()
    val reviewer = new ReviewService[F](finder)
    
    reviewer.reviewTrafficLights().compile.drain
  }


  override def run: IO[Unit] = executeReview[IO]()

}
