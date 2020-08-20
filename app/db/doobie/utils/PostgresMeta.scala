package db.doobie.utils

import java.time.{Instant, LocalDate}
import java.util.{Date, UUID}

import doobie.postgres.{Instances, free, syntax}
import doobie.util.meta._
import io.circe.Json
import doobie.postgres.implicits._
import org.postgresql.util.PGobject
import cats.implicits._
import io.circe.jawn._
import io.circe.syntax._
import models.Users
import org.joda.time.{DateTime, LocalDate => JodaLocalDate}

trait PostgresMeta
// Extracted from doobie.implicits.javasql._
  extends MetaConstructors
    with SqlMetaInstances
    with TimeMetaInstances
    // Extracted from doobies.postgres.implicit._
    with Instances
    with free.Instances
    with syntax.ToPostgresMonadErrorOps
    with syntax.ToFragmentOps
    with syntax.ToPostgresExplainOps {

  implicit protected val jsonMeta: Meta[Json] =
    Meta.Advanced.other[PGobject]("jsonb").timap[Json](
      a => parse(a.getValue).leftMap[Json](e => throw e).merge)(
      a => {
        val o = new PGobject
        o.setType("json")
        o.setValue(a.noSpaces)
        o
      }
    )


//  implicit protected val uuidMeta: Meta[UUID] = Meta[String].timap[UUID](UUID.fromString)(_.toString)

  implicit protected val dateTimeMeta: Meta[DateTime] = DateMeta.timap(d => DateTime.parse(d.toString))(date => new java.sql.Date(date.getMillis))
}
