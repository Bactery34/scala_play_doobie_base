package dao

import cats.effect.IO
import ids.Plop
import models.Errors

trait CRUDDAO[T, A] {

  def list(): IO[Seq[T]]

  def get(id: A): IO[Option[T]]

  def getByName(name: String): IO[Option[T]]

  def create(entity: T): IO[Either[Errors, Int]]

  def update(entity: T): IO[Plop]

  def delete(id: A): IO[Plop]
}
