import cats.effect.IO
import cats.effect.testing.specs2.CatsEffect
import org.specs2.mutable.Specification
import application.ReviewService
import domain.models.Street

class TrafficLightsSpec extends Specification with CatsEffect {

  val streets: List[Street] = List.empty

  "Yellow Traffic Light" should {
    "return changes" in ReviewService[IO].reviewTrafficLights(streets) {
      true must beTrue
    }
  }

}