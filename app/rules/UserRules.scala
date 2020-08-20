package rules

import models.{ErrorString, Errors, Users}
import uk.gov.hmrc.emailaddress.EmailAddress

object UserRules {

  def validationRules(user: Users): Errors = {
    val invalidUsername = user.username match {
      case u if u.length < 4 || u.length > 30 => Some(ErrorString.notInCharacterRanger)
      case _ => None
    }

    val invalidEmail = EmailAddress.isValid(user.email) match {
      case true => None
      case false => Some(ErrorString.invalidEmail)
    }

    val invalidPassword = user.password match {
      case p if p.size < 8 || p.size > 100 => Some(ErrorString.notInCharacterRanger)
      case _ => None
    }

    Errors(Seq(invalidUsername, invalidEmail, invalidPassword).flatten)
  }
}
