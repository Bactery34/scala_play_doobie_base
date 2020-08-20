package dao

import cats.effect.IO
import db.doobie.DoobieDatabase
import db.doobie.utils.PostgresMeta
import ids.{Plop, UserId}
import models.{ErrorString, Errors, Users}
import org.joda.time.DateTime
import doobie.implicits._
import doobie.postgres._
import doobie.postgres.implicits._
import doobie.postgres.sqlstate
import javax.inject.Inject



class UserDAO @Inject()(doobieDatabase: DoobieDatabase) extends CRUDDAO[Users, UserId] {

  import doobieDatabase._

  override def list(): IO[Seq[Users]] = {
    transactor.use { xa =>
      UserDAO.listQuery()
        .to[Seq]
        .transact(xa)
    }
  }

  override def get(id: UserId): IO[Option[Users]] = {
    transactor.use { xa =>
      UserDAO.getByIdQuery(id)
        .option
        .transact(xa)
    }
  }

  override def getByName(name: String): IO[Option[Users]] = {
    transactor.use { xa =>
      UserDAO.getByNameQuery(name)
        .option
        .transact(xa)
    }
  }

  override def create(entity: Users): IO[Either[Errors, Int]] = {
    transactor.use { xa =>
      UserDAO.createQuery(entity)
        .run
        .transact(xa)
    }.attemptSomeSqlState {
      case sqlstate.class23.UNIQUE_VIOLATION => Errors(Seq(ErrorString.alreadyRegisteredUserName))
    }
  }

  override def update(entity: Users): IO[Plop] = {
    transactor.use { xa =>
      UserDAO.updateQuery(entity)
        .run
        .transact(xa)
    }.map(_ => Plop)
  }

  override def delete(id: UserId): IO[Plop] = {
    transactor.use { xa =>
      UserDAO.deleteQuery(id)
        .run
        .transact(xa)
    }.map(_ => Plop)  }
}

object UserDAO extends PostgresMeta {

  def listQuery() = {
    sql"""SELECT id, username, password, email, created_at, updated_at
          FROM users
          ORDER BY username
       """
      .query[Users]
  }

  def getByIdQuery(id: UserId)= {
    sql"""SELECT id, username, password, email, created_at, updated_at
          FROM users
          WHERE id = $id
       """
      .query[Users]
  }

  def getByNameQuery(name: String)= {
    sql"""SELECT id, username, password, email, created_at, updated_at
          FROM users
          WHERE LOWER(username) = ${name.toLowerCase}
       """
      .query[Users]
  }

  def createQuery(user: Users) = {
    sql"""INSERT INTO users
            (id, username, password, email, created_at, updated_at)
          VALUES
            (${user.id}, ${user.username}, ${user.password}, ${user.email}, ${DateTime.now()}, ${DateTime.now()})
      """
      .update
  }

  def updateQuery(user: Users) = {
    sql"""UPDATE users
           SET
            password = ${user.password}
            email = ${user.email}
            updatedAt = ${DateTime.now()}
           WHERE id = ${user.id}
       """
      .update
  }

  def deleteQuery(id: UserId) = {
    sql"""DELETE FROM users
           WHERE id = ${id}"""
      .update
  }
}
