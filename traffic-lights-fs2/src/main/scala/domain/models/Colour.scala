package domain.models

sealed trait Colour

object Colour {

  case object GREEN  extends Colour
  case object RED    extends Colour
  case object YELLOW extends Colour

}
