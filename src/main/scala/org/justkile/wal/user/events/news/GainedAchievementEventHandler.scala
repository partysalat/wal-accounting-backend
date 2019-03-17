package org.justkile.wal.user.events.news

import cats.effect.Sync
import cats.implicits._
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.event_sourcing.event_bus.EventBus.EventHandler
import org.justkile.wal.user.algebras.NewsRepository
import org.justkile.wal.user.domain.User.AchievementGained
import org.justkile.wal.user.http.websocket.NewsWebsocketQueue
import org.justkile.wal.utils.Done

class GainedAchievementEventHandler[F[_]: Sync: Logger: NewsRepository: NewsWebsocketQueue]
    extends EventHandler[F, AchievementGained] {
  def handle(event: AchievementGained): F[Done] =
    for {
      _ <- Logger[F].info(s"Achievement Gained $event")
      res <- NewsRepository[F].addAchievement(event.userId, event.achievementId)
      drinkNews <- res.map(news => NewsRepository[F].getNewsItem(news.id)).sequence
      _ <- drinkNews.map(NewsWebsocketQueue[F].publish(_)).sequence
    } yield Done
}
