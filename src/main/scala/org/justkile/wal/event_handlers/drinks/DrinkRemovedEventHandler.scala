package org.justkile.wal.event_handlers.drinks

import cats.effect.Sync
import cats.implicits._
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.event_sourcing.event_bus.EventBus.EventHandler
import org.justkile.wal.domain.User.UserDrinkRemoved
import org.justkile.wal.http.websocket.NewsWebsocketQueue
import org.justkile.wal.projections.domain.RemoveNews
import org.justkile.wal.projections.NewsRepository
import org.justkile.wal.utils.Done

class DrinkRemovedEventHandler[F[_]: Sync: Logger: NewsRepository: NewsWebsocketQueue]
    extends EventHandler[F, UserDrinkRemoved] {
  def handle(event: UserDrinkRemoved): F[Done] =
    for {
      _ <- Logger[F].info(s"UserDrinkRemoved $event")
      _ <- NewsRepository[F].removeNews(event.userId, event.newsId)
      _ <- NewsWebsocketQueue[F].publish(RemoveNews())
    } yield Done
}
