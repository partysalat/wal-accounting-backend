package org.justkile.wal.event_handlers.news

import cats.effect.Sync
import cats.implicits._
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.domain.UserEvents.AchievementRemoved
import org.justkile.wal.event_sourcing.event_bus.EventBus.EventHandler
import org.justkile.wal.http.websocket.NewsWebsocketQueue
import org.justkile.wal.projections.NewsRepository
import org.justkile.wal.projections.domain.RemoveNews
import org.justkile.wal.utils.Done

class AchievementRemovedEventHandler[F[_]: Sync: Logger: NewsRepository: NewsWebsocketQueue]
    extends EventHandler[F, AchievementRemoved] {
  def handle(event: AchievementRemoved): F[Done] =
    for {
      _ <- Logger[F].info(s"Achievement Gained $event")
      _ <- NewsRepository[F].removeAchievement(event.userId, event.achievementId)
      _ <- NewsWebsocketQueue[F].publish(RemoveNews())
    } yield Done
}
