package org.justkile.wal.user.events.achievements
import cats.{Applicative, Traverse}
import cats.syntax.functor._
import cats.syntax.flatMap._
import cats.implicits.catsStdInstancesForList
import cats.effect.Sync
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.event_sourcing.{AggregateRepository, CommandProcessor, Event}
import org.justkile.wal.event_sourcing.event_bus.EventBus.EventHandler
import org.justkile.wal.user.algebras.{AchievementRepository, UserRepository}
import org.justkile.wal.user.domain.User
import org.justkile.wal.user.domain.User.{GainAchievement, UserCreated, UserDrinkAdded, UserIdentifier}
import org.justkile.wal.utils.Done

class AchievementHandler[F[_]: Sync: Logger: Applicative: AchievementRepository: CommandProcessor: AggregateRepository]
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
      stats <- AchievementRepository[F].getDrinkEventsForUser(userId)
      aggRes <- AggregateRepository[F].load(UserIdentifier(userId))
      (userAgg, _) = aggRes
      reachableAchievements = AchievementDefinitions.eventBaseAchievements
        .filterNot(achievementDef => userAgg.achievements.contains(achievementDef.achievement.id))

      reachedAchievements = reachableAchievements.filter(_.predicate(stats))
      achievementCommands = reachedAchievements.map(ach => GainAchievement(userId, ach.achievement.id))
      _ <- Traverse[List].sequence(achievementCommands.map(CommandProcessor[F].process[User](_)))
    } yield ()

  }

}
