package loader

import java.util.UUID

import ids.{InternalId, UserId}
import play.api.mvc.PathBindable

trait Route {

  private def internalIdpathBindValueClass[T <: InternalId](f: UUID => T)(implicit uuidBinder: PathBindable[UUID]): PathBindable[T] = new PathBindable[T] {
    override def bind(key: String, value: String): Either[String, T] = {
      for {
        id <- uuidBinder.bind(key, value)
      } yield f(id)
    }

    override def unbind(key: String, id: T): String = {
      uuidBinder.unbind(key, id.value)
    }
  }

  implicit def userIdpathBindValueClass(implicit uuidBinder: PathBindable[UUID]): PathBindable[UserId] = internalIdpathBindValueClass(UserId)
}

object Route extends Route
