package rules

import models.{ErrorString, Errors, NewUser, Users}
import uk.gov.hmrc.emailaddress.EmailAddress

object NewUserRules {

  def validationRules(user: NewUser): Errors = {
    val invalidUsername = user.username match {
      case u if u.length < 4 || u.length > 30 => Some(ErrorString.notInCharacterRanger)
      case _ => None
    }

    val invalidEmail = {
      if(!EmailAddress.isValid(user.email)) Some(ErrorString.invalidEmail)
      else None
    }

    val invalidPassword = user.password match {
      case p if p.size < 8 || p.size > 100 => Some(ErrorString.passwordTooShortOrLong)
      case p if p != user.password2 => Some(ErrorString.differentPassword)
      case _ => None
    }

    Errors(Seq(invalidEmail, invalidUsername, invalidPassword).flatten)
  }
}
