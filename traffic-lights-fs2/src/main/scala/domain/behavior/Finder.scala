package domain.behavior

import domain.models.Street

/**
 * Interface in charge of looking for traffic lights in yellow
 * @tparam F Effect
 */
trait Finder[F[_]] {
  def findStreetsWithYellowTL(): F[List[Street]]
}
