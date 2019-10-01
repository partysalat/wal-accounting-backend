package org.justkile.wal.user.events.achievements
import cats.Traverse
import cats.effect.Sync
import cats.implicits.catsStdInstancesForList
import cats.syntax.flatMap._
import cats.syntax.functor._
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.event_sourcing.event_bus.EventBus.EventHandler
import org.justkile.wal.event_sourcing.{AggregateRepository, CommandProcessor}
import org.justkile.wal.user.algebras.AchievementRepository
import org.justkile.wal.user.domain.User
import org.justkile.wal.user.domain.User.{GainAchievement, UserDrinkAdded, UserIdentifier}
import org.justkile.wal.utils.Done

class AchievementHandler[F[_]: Sync: Logger: AchievementRepository: CommandProcessor: AggregateRepository]
    extends EventHandler[F, UserDrinkAdded] {
  def handle(event: UserDrinkAdded): F[Done] =
    for {
      _ <- handleUserDrinkAddedEvent(event)
    } yield Done

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
