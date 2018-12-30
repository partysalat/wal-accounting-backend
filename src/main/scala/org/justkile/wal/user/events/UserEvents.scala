package org.justkile.wal.user.events

import cats.effect._
import cats.implicits._
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.event_sourcing.event_bus.EventBus
import org.justkile.wal.user.events.user_created.UserCreatedEventHandler

class UserEvents[F[_]: Sync: EventBus: Logger] {

  def start: F[Unit] =
    for {
      _ <- EventBus[F].subscribe(new UserCreatedEventHandler[F])
    } yield ()
}
