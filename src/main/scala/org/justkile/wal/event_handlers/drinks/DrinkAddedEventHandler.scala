package org.justkile.wal.event_handlers.drinks

import cats.effect.Sync
import cats.implicits._
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.domain.UserEvents.UserDrinkAdded
import org.justkile.wal.event_sourcing.event_bus.EventBus.EventHandler
import org.justkile.wal.http.websocket.NewsWebsocketQueue
import org.justkile.wal.projections.NewsRepository
import org.justkile.wal.utils.Done

class DrinkAddedEventHandler[F[_]: Sync: Logger: NewsRepository: NewsWebsocketQueue]
    extends EventHandler[F, UserDrinkAdded] {
  def handle(event: UserDrinkAdded): F[Done] =
    for {
      _ <- Logger[F].info(s"UserDrinkAdded $event")
      res <- NewsRepository[F].addDrinkNews(event.userId, event.drinkId, event.amount)
      drinkNews <- res.map(news => NewsRepository[F].getNewsItem(news.id)).sequence
      _ <- drinkNews.map(NewsWebsocketQueue[F].publish(_)).sequence
      _ <- Logger[F].info(s"UserDrinkAdded result $res")
    } yield Done
}
