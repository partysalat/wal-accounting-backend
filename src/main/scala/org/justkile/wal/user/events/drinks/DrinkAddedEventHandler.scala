package org.justkile.wal.user.events.drinks

import cats.effect.Sync
import cats.implicits._
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.event_sourcing.event_bus.EventBus.EventHandler
import org.justkile.wal.user.algebras.NewsRepository
import org.justkile.wal.user.domain.User.UserDrinkAdded
import org.justkile.wal.utils.Done

class DrinkAddedEventHandler[F[_]: Sync: Logger: NewsRepository] extends EventHandler[F, UserDrinkAdded] {
  def handle(event: UserDrinkAdded): F[Done] =
    for {
      _ <- Logger[F].info(s"UserDrinkAdded $event")
      res <- NewsRepository[F].addDrinkNews(event.userId, event.drinkId, event.amount)
      _ <- Logger[F].info(s"UserDrinkAdded result $res")
    } yield Done
}
