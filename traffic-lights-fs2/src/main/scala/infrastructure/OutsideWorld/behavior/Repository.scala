package infrastructure.OutsideWorld.behavior

import domain.models.{Street, TrafficLights}
import fs2.{Pipe, Stream}
import infrastructure.models.responses.ReportResponse

/** Interface in charge of looking for data abroad
  *
  * @tparam F
  *   Effect
  */
trait Repository[F[_]] {

  /** Method to search for streets with yellow traffic lights
    * @return  Effect with [[domain.models.street]] list
    */
  def findStreetsWithYellowTL(): Stream[F, Street]

  /** Method to search for streets with yellow traffic lights
    * @return Effect with [[domain.models.TrafficLights]] list
    */
  def findReportChanges: Pipe[F, Street, TrafficLights]

}
