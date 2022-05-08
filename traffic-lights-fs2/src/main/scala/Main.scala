import application.{FinderService, ReviewService}
import cats.effect.kernel.Resource
import cats.effect.{Async, IO, IOApp}
import doobie.*
import doobie.hikari.*
import infrastructure.DbConfiguration
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger


object Main extends IOApp.Simple {

  def executeReview[F[_]: Async](config: DbConfiguration) = {

    implicit val logger: Logger[F] =
      Slf4jLogger.getLogger[F]

    implicit val transactor: Resource[F, HikariTransactor[F]] =
        for {
          ce <- ExecutionContexts.fixedThreadPool[F](32) // our connect EC
          xa <- HikariTransactor.newHikariTransactor[F](
            "com.mysql.cj.jdbc.Driver", // driver classname
            "jdbc:mysql://localhost:3307/street", // connect URL
            "root", // username
            "root", // password
            ce // await connection here
          )
        } yield xa

    val finder = new FinderService[F]
    val reviewer = new ReviewService[F](finder)

    reviewer.reviewTrafficLights().compile.drain
  }

  val dbConfig: DbConfiguration = DbConfiguration()

  override def run: IO[Unit] = executeReview[IO](dbConfig)

}
