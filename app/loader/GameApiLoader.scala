package loader

import akka.actor.ActorSystem
import com.typesafe.config.Config
import controllers.UserController
import dao.UserDAO
import db.doobie.DoobieDatabase
import db.doobie.config.DatabaseConfig
import javax.inject.Inject
import play.api.ApplicationLoader.Context
import play.api.db.{DBComponents, HikariCPComponents}
import play.api.db.evolutions.EvolutionsComponents
import play.api.routing.Router
import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext, LoggerConfigurator}
import services.UserService
import router.Routes

import scala.concurrent.ExecutionContext

class GameApiLoader @Inject() extends ApplicationLoader {

  def load(context: Context): Application = {
    LoggerConfigurator(context.environment.classLoader).foreach {
      _.configure(context.environment)
    }
    new ApplicationComponents(context)
  }.application
}

class ApplicationComponents(context: Context)
  extends BuiltInComponentsFromContext(context)
    with play.filters.HttpFiltersComponents
    with _root_.controllers.AssetsComponents
    with DBComponents
    with EvolutionsComponents
    with HikariCPComponents
    with Configs {

  lazy val doobieDatabaseConfig = DatabaseConfig.fromConfig(config.getConfig("database"))

  lazy val doobieDb: DoobieDatabase = new DoobieDatabase(doobieDatabaseConfig)

  lazy val userDAO = new UserDAO(doobieDb)

  lazy val userService = new UserService(userDAO)

  lazy val userController = new UserController(userService)(controllerComponents, actorSystem)

  // add the prefix string in local scope for the Routes constructor
  lazy val router: Router = {
    val prefix: String = "/"
    new Routes(httpErrorHandler, userController)
  }

  //apply evolutions on start up
  applicationEvolutions.start()
}