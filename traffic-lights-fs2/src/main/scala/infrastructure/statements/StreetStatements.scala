package infrastructure.statements

object StreetStatements {
  
  val FIND_STREETS_WITH_YELLOWS_TL: String =
    "SELECT s.* FROM traffic_lights t JOIN street s ON s.id = t.street_id WHERE t.status = 'YELLOW' GROUP BY s.id;"

}
