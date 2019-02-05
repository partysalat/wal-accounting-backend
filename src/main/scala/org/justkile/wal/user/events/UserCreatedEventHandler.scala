package org.justkile.wal.user.events

import cats.effect.Sync
import cats.implicits._
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.event_sourcing.event_bus.EventBus.EventHandler
import org.justkile.wal.user.algebras.UserRepository
import org.justkile.wal.user.domain.User.UserCreated
import org.justkile.wal.utils.Done

class UserCreatedEventHandler[F[_]: Sync: Logger: UserRepository] extends EventHandler[F, UserCreated] {
  def handle(event: UserCreated): F[Done] =
    for {
      _ <- Logger[F].info(s"UserCreatedEventHandler $event")
      _ <- UserRepository[F].addUser(event.id, event.name)
    } yield Done
}
