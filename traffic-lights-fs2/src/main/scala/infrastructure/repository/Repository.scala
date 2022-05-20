package infrastructure.repository

import domain.models.{ Street, TrafficLights }
import fs2.{ Pipe, Stream }

/** Interface in charge of looking for data abroad
  *
  * @tparam F
  *   Effect
  */
trait Repository[F[_]] {

  /** Method to search for streets with yellow traffic lights
    * @return
    *   Effect with [[domain.models.street]] list
    */
  def findStreetsWithYellowTL(): Stream[F, Street]

}
