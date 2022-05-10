package infrastructure.models.configurations

case class DbConfiguration(
    driver: String = "",
    url: String = "",
    user: String = "",
    pass: String = ""
)
