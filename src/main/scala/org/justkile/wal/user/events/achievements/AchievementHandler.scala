package org.justkile.wal.user.events.achievements
import cats.effect.Sync
import cats.syntax.functor._

import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.event_sourcing.Event
import org.justkile.wal.event_sourcing.event_bus.EventBus.EventHandler
import org.justkile.wal.utils.Done

class AchievementHandler[F[_]: Sync: Logger] extends EventHandler[F, Event] {
  def handle(event: Event): F[Done] =
    for {
      _ <- Logger[F].info(s"UserDrinkAdded $event")
    } yield Done
}
