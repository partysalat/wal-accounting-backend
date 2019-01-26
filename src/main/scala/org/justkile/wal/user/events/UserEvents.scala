package org.justkile.wal.user.events

import cats.effect._
import cats.implicits._
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.event_sourcing.{AggregateRepository, CommandProcessor}
import org.justkile.wal.event_sourcing.event_bus.EventBus
import org.justkile.wal.user.algebras.{AchievementRepository, NewsRepository, UserRepository}
import org.justkile.wal.user.events.achievements.AchievementHandler
import org.justkile.wal.user.events.news.GainedAchievementEventHandler

class UserEvents[
    F[_]: Sync: EventBus: Logger: UserRepository: NewsRepository: AchievementRepository: CommandProcessor: AggregateRepository] {

  def start: F[Unit] =
    for {
      _ <- EventBus[F].subscribe(new UserCreatedEventHandler[F])
      _ <- EventBus[F].subscribe(new DrinkAddedEventHandler[F])
      _ <- EventBus[F].subscribe(new GainedAchievementEventHandler[F])
      _ <- EventBus[F].subscribe(new DrinkRemovedEventHandler[F])
      _ <- EventBus[F].subscribe(new AchievementHandler[F])
    } yield ()
}
