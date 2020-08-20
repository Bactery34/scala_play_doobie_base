package loader

import akka.actor.ActorSystem
import com.typesafe.config.Config
import play.api.BuiltInComponentsFromContext

import scala.concurrent.ExecutionContext

trait Core extends BuiltInComponentsFromContext {
  implicit val system: ActorSystem = ActorSystem("game")
  implicit val ec: ExecutionContext = controllerComponents.executionContext
  val config: Config = configuration.underlying
}
