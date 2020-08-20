package services

import cats.effect.IO
import dao.UserDAO
import ids.{Plop, UserId}
import javax.inject.Inject
import models.{Errors, NewUser, Users}
import rules.{NewUserRules, UserRules}

class UserService @Inject() (userDAO: UserDAO) extends CRUDService[Users, UserId, NewUser] {

  override def list(): IO[Seq[Users]] = userDAO.list()

  override def get(id: UserId): IO[Option[Users]] = userDAO.get(id)

  override def getByName(name: String): IO[Option[Users]] = userDAO.getByName(name)

  override def create(user: NewUser): IO[Either[Errors, Plop]] = {
    NewUserRules.validationRules(user) match {
      case error if error.isEmpty() => {
        val newUser = user.toUser
        userDAO.create(newUser)
          .map {
            case Right(_) => Right(Plop)
            case Left(errors) => Left(errors)
          }
      }
      case error => IO(Left(error))
    }
  }

  override def update(user: Users): IO[Either[Errors, Plop]] = {
    UserRules.validationRules(user) match {
      case error if error.isEmpty() => userDAO.update(user).map(_ => Right(Plop))
      case error => IO(Left(error))
    }
  }

  override def delete(id: UserId): IO[Plop] = userDAO.delete(id)
}
