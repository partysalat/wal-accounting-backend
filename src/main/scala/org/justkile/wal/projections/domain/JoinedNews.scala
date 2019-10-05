package org.justkile.wal.projections.domain

import io.circe.{Decoder, Encoder}
import org.justkile.wal.core.drinks.domain.DrinkType.DrinkType

sealed trait FrontendNews

sealed trait NewsPayload
case class DrinkPayload(id: Int, name: String, `type`: DrinkType) extends NewsPayload
case class AchievementPayload(id: Int, name: String, imagePath: String, description: String) extends NewsPayload
case class RemoveNewsPayload() extends NewsPayload

object NewsPayload {
  implicit val encodeNewsPayload: Encoder[NewsPayload] = io.circe.generic.semiauto.deriveEncoder[NewsPayload]
  implicit val decodeNewsPayload: Decoder[NewsPayload] = io.circe.generic.semiauto.deriveDecoder[NewsPayload]
}

case class JoinedNews(
    news: News,
    user: UserProjection,
    payload: NewsPayload
) extends FrontendNews

object JoinedNews {
  def from(res: (News, UserProjection, Option[DrinkPayload], Option[AchievementPayload])): JoinedNews = {
    JoinedNews(res._1,
               res._2,
               List(res._3, res._4)
                 .find(_.isDefined)
                 .flatten
                 .get)

  }
}

case class RemoveNews() extends FrontendNews
