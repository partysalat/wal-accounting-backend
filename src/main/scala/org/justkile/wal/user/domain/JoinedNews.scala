package org.justkile.wal.user.domain

import io.circe.{Decoder, Encoder}
import org.justkile.wal.drinks.domain.DrinkType.DrinkType

sealed trait NewsPayload
case class DrinkPayload(id: Int, name: String, `type`: DrinkType) extends NewsPayload
case class AchievementPayload(id: Int, name: String, description: String, imagePath: String) extends NewsPayload

object NewsPayload {
  implicit val encodeNewsPayload: Encoder[NewsPayload] = io.circe.generic.semiauto.deriveEncoder[NewsPayload]
  implicit val decodeNewsPayload: Decoder[NewsPayload] = io.circe.generic.semiauto.deriveDecoder[NewsPayload]
}

case class JoinedNews(
    news: News,
    user: UserProjection,
    payload: NewsPayload
)
