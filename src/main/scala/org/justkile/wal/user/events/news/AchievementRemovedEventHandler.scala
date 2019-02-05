package org.justkile.wal.user.events.news

import cats.effect.Sync
import cats.implicits._
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.event_sourcing.event_bus.EventBus.EventHandler
import org.justkile.wal.user.algebras.NewsRepository
import org.justkile.wal.user.domain.User.AchievementRemoved
import org.justkile.wal.utils.Done

class AchievementRemovedEventHandler[F[_]: Sync: Logger: NewsRepository] extends EventHandler[F, AchievementRemoved] {
  def handle(event: AchievementRemoved): F[Done] =
    for {
      _ <- Logger[F].info(s"Achievement Gained $event")
      _ <- NewsRepository[F].removeAchievement(event.userId, event.achievementId)
    } yield Done
}
