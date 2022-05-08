package domain.models

sealed trait Colour

object Colour {

  final case object GREEN  extends Colour
  final case object RED    extends Colour
  final case object YELLOW extends Colour

}
