package infrastructure

import domain.models.{ Street, TrafficLights }

/** Interface in charge of looking for data abroad
  *
  * @tparam F
  *   Effect
  */
trait Finder[F[_]] {

  /** Method to search for streets with yellow traffic lights
    * @return
    *   Effect with [[domain.models.street]] list
    */
  def findStreetsWithYellowTL(): F[List[Street]]

  /** Method to search for streets with yellow traffic lights
    * @param street
    *   [[domain.models.Street]]
    * @return
    *   Effect with [[domain.models.TrafficLights]] list
    */
  def findTrafficLightsChanges(street: Street): F[List[TrafficLights]]

}
