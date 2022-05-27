import application.services.ReviewService
import cats.effect.IO
import cats.effect.testing.specs2.CatsEffect
import domain.models.{Street, TrafficLights}
import fs2.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import org.scalatest.wordspec.AsyncWordSpec
import org.specs2.matcher.MustMatchers
import org.specs2.mutable.Specification

class TrafficLightsSpec extends AsyncWordSpec with MustMatchers {

  val find: Unit => IO[List[Street]] = _ => IO(List.empty[Street])
  val getChanges: Street => IO[List[TrafficLights]] = _ => IO(List.empty[TrafficLights])

  case class TestScenario(tl: TrafficLights, tls: List[TrafficLights])
  object TestScenario {
    def apply(tl: TrafficLights, tls: List[TrafficLights]): Boolean =
      !tls.exists(t => t.id == tl.id && t.status === "YELLOW")
  }

  "Yellow Traffic Light" should {

    "return changes" in {

      pending

    }
  }

}