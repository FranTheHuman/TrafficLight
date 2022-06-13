import application.services.ReviewService
import cats.effect.IO
import cats.effect.testing.specs2.CatsEffect
import domain.models.{Street, TrafficLights}
//import fs2.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import org.scalatest.wordspec.AsyncWordSpec
import org.specs2.matcher.MustMatchers
import org.specs2.mutable.Specification
import io.circe.syntax._
import io.circe.parser.decode

class TrafficLightsSpec extends AsyncWordSpec with MustMatchers {

  val find: Unit => IO[List[Street]] = _ => IO(List.empty[Street])
  val getChanges: Street => IO[List[TrafficLights]] = _ => IO(List.empty[TrafficLights])

  case class TestScenario(tl: TrafficLights, tls: List[TrafficLights])

  object TestScenario {
    def apply(tl: TrafficLights, tls: List[TrafficLights]): Boolean =
      !tls.exists(t => t.id == tl.id && t.status === "YELLOW")
  }

  "TrafficLight encode and decode" should {
    "return the same object" in {
      val trafficLight = TrafficLights(id = 1, status = "YELLOW", streetId = 10)
      val trafficLightEncoded = trafficLight.asJson
      val trafficLightDecoded = decode[TrafficLights](trafficLightEncoded.toString())
      println(trafficLightEncoded)
      println(trafficLightDecoded)
      assert{
        Right(trafficLight) == trafficLightDecoded
      }
    }
  }

  "Yellow Traffic Light" should {
    "return changes" in {
      pending
    }
  }


}