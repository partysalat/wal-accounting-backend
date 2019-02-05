package org.justkile.wal.user.domain

import doobie.util.meta.Meta
import io.circe.{Decoder, Encoder}
import org.justkile.wal.user.domain.NewsType.NewsType

object NewsType extends Enumeration {
  type NewsType = Value
  val DRINK = Value("DRINK")
  val ACHIEVEMENT = Value("ACHIEVEMENT")
  val IMAGE = Value("IMAGE")

  implicit val newsTypeMeta: Meta[NewsType] = Meta[String]
    .xmap((s: String) => NewsType.withName(s), (d: NewsType) => d.toString)

  implicit val newsTypeDecoder: Decoder[NewsType] = Decoder.enumDecoder(NewsType)
  implicit val newsTypeEncoder: Encoder[NewsType] = Encoder.enumEncoder(NewsType)
}

case class News(
    id: Int,
    newsType: NewsType,
    userId: String,
    amount: Int,
    referenceId: Int
)
