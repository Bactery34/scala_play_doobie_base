package services

import cats.effect.IO
import ids.Plop
import models.Errors

trait CRUDService[T, A, B] {

  def list(): IO[Seq[T]]

  def get(id: A): IO[Option[T]]

  def getByName(name: String): IO[Option[T]]

  def create(entity: B): IO[Either[Errors, Plop]]

  def update(entity: T): IO[Either[Errors, Plop]]

  def delete(id: A): IO[Plop]
}
