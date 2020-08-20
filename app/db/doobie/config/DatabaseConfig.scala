package db.doobie.config

import com.typesafe.config.Config
import javax.inject.Inject

case class DatabaseConfig @Inject()(driver: String, url: String, user: String, password: String)

object DatabaseConfig {

  def fromConfig(config: Config): DatabaseConfig = {
    DatabaseConfig(
      driver = config.getString("driver"),
      url = config.getString("url"),
      user = config.getString("user"),
      password = config.getString("password")
    )
  }
}