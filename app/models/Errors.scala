package models

import io.circe._
import io.circe.generic.semiauto._

case class Errors(errorsString: Seq[String]){
  def isEmpty() = errorsString.isEmpty
}

object Errors {

  implicit val errorsDecoder: Decoder[Errors] = deriveDecoder
  implicit val errorsEncoder: Encoder[Errors] = deriveEncoder
}

object ErrorString {

  // Users
  val alreadyRegisteredUserName = "users.username.already.registered"
  val differentPassword = "users.password.different"
  val notInCharacterRanger = "users.username.not.in.character.range"
  val passwordTooShortOrLong = "users.password.not.in.character.range"
  val invalidEmail = "users.email.invalid"
}