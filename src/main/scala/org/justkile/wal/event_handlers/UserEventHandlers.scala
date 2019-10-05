package org.justkile.wal.event_handlers

import cats.effect._
import cats.implicits._
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.event_sourcing.{AggregateRepository, CommandProcessor}
import org.justkile.wal.event_sourcing.event_bus.EventBus
import org.justkile.wal.event_handlers.achievements.{
  AchievementHandler,
  AchievementUserDrinkRemovedHandler,
  SpaceInvadersAchievementHandler
}
import org.justkile.wal.event_handlers.bestlist.BestlistStatsEventHandler
import org.justkile.wal.event_handlers.drinks.{DrinkAddedEventHandler, DrinkRemovedEventHandler}
import org.justkile.wal.event_handlers.news.{AchievementRemovedEventHandler, GainedAchievementEventHandler}
import org.justkile.wal.http.websocket.NewsWebsocketQueue
import org.justkile.wal.event_handlers.user.UserCreatedEventHandler
import org.justkile.wal.projections.{AchievementRepository, BestlistRepository, NewsRepository, UserRepository}

class UserEventHandlers[
    F[_]: Sync: EventBus: Logger: UserRepository: NewsRepository: AchievementRepository: CommandProcessor: AggregateRepository: BestlistRepository](
    websocketQueue: NewsWebsocketQueue[F]) {
  implicit val queue = websocketQueue
  def start: F[Unit] =
    for {
      _ <- EventBus[F].subscribe(new UserCreatedEventHandler[F])
      _ <- EventBus[F].subscribe(new BestlistStatsEventHandler[F])
      _ <- EventBus[F].subscribe(new DrinkAddedEventHandler[F])
      _ <- EventBus[F].subscribe(new GainedAchievementEventHandler[F])
      _ <- EventBus[F].subscribe(new DrinkRemovedEventHandler[F])
      _ <- EventBus[F].subscribe(new AchievementHandler[F])
      _ <- EventBus[F].subscribe(new AchievementRemovedEventHandler[F])
      _ <- EventBus[F].subscribe(new AchievementUserDrinkRemovedHandler[F])
      _ <- EventBus[F].subscribe(new SpaceInvadersAchievementHandler[F])
    } yield ()
}
