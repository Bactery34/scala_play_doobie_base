package loader

import db.doobie.config.DatabaseConfig

trait Configs extends Core {
  lazy val databaseConfig: DatabaseConfig = DatabaseConfig.fromConfig(config.getConfig("database"))
}
