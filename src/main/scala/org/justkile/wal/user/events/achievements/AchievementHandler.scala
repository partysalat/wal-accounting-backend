package org.justkile.wal.user.events.achievements
import cats.{Applicative, Traverse}
import cats.syntax.functor._, cats.syntax.flatMap._, cats.implicits.catsStdInstancesForList
import cats.effect.Sync
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.event_sourcing.{CommandProcessor, Event}
import org.justkile.wal.event_sourcing.event_bus.EventBus.EventHandler
import org.justkile.wal.user.algebras.AchievementRepository
import org.justkile.wal.user.domain.User
import org.justkile.wal.user.domain.User.{GainAchievement, UserCreated, UserDrinkAdded}
import org.justkile.wal.utils.Done

import scala.collection.immutable

class AchievementHandler[F[_]: Sync: Logger: Applicative: AchievementRepository: CommandProcessor]
    extends EventHandler[F, Event] {
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
      _ <- AchievementRepository[F].saveUserStats(userId, drinkId, amount)
      stats <- AchievementRepository[F].getStatsForUser(userId)
      //filter out already reached achievements
      reachedAchievements = AchievementDefinitions.eventBaseAchievements.filter(_.predicate(stats))
      achievementCommands = reachedAchievements.map(ach => GainAchievement(userId, ach.achievement.id))
      _ <- Traverse[List].sequence(achievementCommands.map(CommandProcessor[F].process[User](_)))
    } yield ()

  }

}
