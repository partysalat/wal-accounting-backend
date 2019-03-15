package org.justkile.wal.user.events

import cats.effect._
import cats.implicits._
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.event_sourcing.{AggregateRepository, CommandProcessor}
import org.justkile.wal.event_sourcing.event_bus.EventBus
import org.justkile.wal.user.algebras.{AchievementRepository, BestlistRepository, NewsRepository, UserRepository}
import org.justkile.wal.user.events.achievements.{AchievementHandler, AchievementUserDrinkRemovedHandler}
import org.justkile.wal.user.events.bestlist.BestlistStatsEventHandler
import org.justkile.wal.user.events.drinks.{DrinkAddedEventHandler, DrinkRemovedEventHandler}
import org.justkile.wal.user.events.news.{AchievementRemovedEventHandler, GainedAchievementEventHandler}
import org.justkile.wal.user.http.websocket.NewsWebsocketQueue

class UserEvents[
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
    } yield ()
}
