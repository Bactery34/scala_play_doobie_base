package models

import java.util.UUID

import ids.UserId
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._
import org.joda.time.DateTime


case class NewUser(username: String, password: String, password2: String, email: String) {
  def toUser: Users = {
    val id = UserId(UUID.randomUUID())

    Users(id, username, password, email, DateTime.now(), DateTime.now())
  }
}

object NewUser {

  implicit val newUserDecoder: Decoder[NewUser] = deriveDecoder[NewUser]
  implicit val newUserEncoder: Encoder[NewUser] = deriveEncoder[NewUser]
}

