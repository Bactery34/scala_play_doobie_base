import java.util.UUID

import io.circe.KeyEncoder
import play.api.mvc.PathBindable

package object ids {

  implicit def pathBinder(implicit stringBinder: PathBindable[String]) = new PathBindable[UUID] {
    override def bind(key: String, value: String): Either[String, UUID] = {
      for {
        id   <- stringBinder.bind(key, value).right
      } yield UUID.fromString(id)
    }
    override def unbind(key: String, uuid: UUID): String = {
      uuid.toString
    }
  }

  // PLOP MAGIC TYPE
  object Plop
  type Plop = Plop.type

  trait InternalId extends Any {
    def value: UUID
    def binVal: String = toString
    override def toString: String = value.toString
  }

  type UUID = java.util.UUID

  case class UserId(value: UUID) extends AnyVal with InternalId

  implicit val userIdEncoder: KeyEncoder[UserId] = new KeyEncoder[UserId] {
    override def apply(userId: UserId): String = userId.value.toString
  }
}
