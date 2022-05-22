package domain.models

import doobie.Read

/**
 * Model that maps an external model to add a semaphore to it
 */
case class Street(id: Int, name: String)

object Street {

  implicit val reader: Read[Street] =
    Read[(Int, String)].map { case (x, y) => new Street(x, y) }

}