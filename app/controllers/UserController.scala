package controllers

import java.util.UUID

import akka.actor.ActorSystem
import ids.UserId
import io.circe.Json
import services.UserService
import play.api.mvc._
import io.circe.syntax._
import javax.inject.Inject
import models.{NewUser, Users}
import play.api.libs.circe.Circe

import scala.concurrent.ExecutionContext

class UserController @Inject()(userService: UserService )(cc: ControllerComponents, actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends AbstractController(cc) with Circe {


  def list()  = Action.async {
    userService.list().unsafeToFuture().map(users => Ok(users.asJson))
  }

  def get(id: UserId) = Action.async {
    userService.get(id).unsafeToFuture().map(user => Ok(user.asJson))
  }

  def getByName(name: String) = Action.async {
    userService.getByName(name).unsafeToFuture().map(user => Ok(user.asJson))
  }

  def create() = Action.async(circe.json[NewUser]) { implicit request =>
    userService.create(request.body).unsafeToFuture().map {
      case Right(_) => Ok(request.body.asJson)
      case Left(errors) => Conflict(errors.asJson)
    }
  }

  def update() = Action.async(circe.json[Users]) { implicit request =>
    userService.update(request.body).unsafeToFuture().map {
      case Right(_) => Ok(request.body.asJson)
      case Left(errors) => Conflict(errors.asJson)
    }
  }

  def delete(id: UserId) = Action.async {
    userService.delete(id).unsafeToFuture().map(_ => Ok)
  }
}
