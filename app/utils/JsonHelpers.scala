package utils

import java.util.UUID

import ids.{InternalId, UserId}
import io.circe.{Decoder, Encoder, HCursor, Json}
import org.joda.time.DateTime

trait JsonHelpers {

  implicit def anyWithEncoderToJsValue[T](field: T)(implicit enc: Encoder[T]): Json = enc(field)

  // Id decoders
  private def idDecoder[T <: InternalId](f: UUID => T): Decoder[T] = Decoder.instance((c: HCursor) => c.as[String].map(UUID.fromString).map(f))
  implicit val userIdDecoder: Decoder[UserId] = idDecoder[UserId](UserId.apply)

  // Id encoders
  private def idEncoder[T <: InternalId]: Encoder[T] = Encoder.instance((obj: InternalId) => Json.fromString(obj.binVal))
  implicit val userIdEncoder: Encoder[UserId] = idEncoder[UserId]

  //date
  implicit val dateTimeEncoder: Encoder[DateTime] = Encoder.instance(value => value.toString)
  implicit val dateTimeDecoder: Decoder[DateTime] = Decoder.instance((c: HCursor) => {
    for {
      dateTime <- c.as[String]
    } yield {
      DateTime.parse(dateTime)
    }
  })
}
