package org.justkile.wal.user.events

import cats.effect._
import cats.implicits._
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.event_sourcing.event_bus.EventBus
import org.justkile.wal.user.algebras.{NewsRepository, UserRepository}

class UserEvents[F[_]: Sync: EventBus: Logger: UserRepository: NewsRepository] {

  def start: F[Unit] =
    for {
      _ <- EventBus[F].subscribe(new UserCreatedEventHandler[F])
      _ <- EventBus[F].subscribe(new DrinkAddedEventHandler[F])
    } yield ()
}
