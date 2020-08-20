package models

import ids.UserId
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._
import org.joda.time.DateTime
import utils.JsonHelpers

case class Users(id: UserId, username: String, email: String, password: String, createdAt: DateTime, updatedAt: DateTime)

object Users extends JsonHelpers {

  implicit val usersDecoder: Decoder[Users] = deriveDecoder[Users]
  implicit val usersEncoder: Encoder[Users] = deriveEncoder[Users]
}
