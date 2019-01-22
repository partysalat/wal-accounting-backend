package org.justkile.wal.user.events.achievements
import cats.Applicative
import cats.syntax.functor._
import cats.effect.Sync
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.event_sourcing.Event
import org.justkile.wal.event_sourcing.event_bus.EventBus.EventHandler
import org.justkile.wal.user.algebras.AchievementRepository
import org.justkile.wal.user.domain.User.{UserCreated, UserDrinkAdded}
import org.justkile.wal.utils.Done

class AchievementHandler[F[_]: Sync: Logger: Applicative: AchievementRepository] extends EventHandler[F, Event] {
  def handle(event: Event): F[Done] =
    for {
      _ <- event match {
        case createUserEvent: UserCreated => handleUserCreatedEvent(createUserEvent)
        case userDrinkAdded: UserDrinkAdded => handleUserDrinkAddedEvent(userDrinkAdded)
        case _ => Applicative[F].pure(Done)
      }
    } yield Done

  private def handleUserCreatedEvent(event: UserCreated): F[Unit] = {
    Logger[F].info(s"user created $event")
  }
  private def handleUserDrinkAddedEvent(event: UserDrinkAdded): F[Unit] = {
    import event._
    for {
//      _ <- Logger[F].info(s"UserDrinkAdded $event")
      _ <- AchievementRepository[F].saveUserStats(userId, drinkId, amount)
    } yield ()

  }

}
