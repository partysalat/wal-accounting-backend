package org.justkile.wal.event_handlers.achievements

import cats.Traverse
import cats.effect.Sync
import cats.implicits.catsStdInstancesForList
import cats.syntax.flatMap._
import cats.syntax.functor._
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.core.achievements.AchievementDefinitions
import org.justkile.wal.domain.User
import org.justkile.wal.domain.User.UserIdentifier
import org.justkile.wal.domain.UserCommands.RemoveAchievement
import org.justkile.wal.domain.UserEvents.UserDrinkRemoved
import org.justkile.wal.event_sourcing.event_bus.EventBus.EventHandler
import org.justkile.wal.event_sourcing.{AggregateRepository, CommandProcessor}
import org.justkile.wal.projections.AchievementRepository
import org.justkile.wal.utils.Done

class AchievementUserDrinkRemovedHandler[
    F[_]: Sync: Logger: AchievementRepository: CommandProcessor: AggregateRepository]
    extends EventHandler[F, UserDrinkRemoved] {
  def handle(event: UserDrinkRemoved): F[Done] =
    for {
      _ <- handleUserDrinkRemovedEvent(event)
    } yield Done

  private def handleUserDrinkRemovedEvent(event: UserDrinkRemoved): F[Unit] = {
    import event._
    for {
      _ <- AchievementRepository[F].subtractUserStats(userId, drinkId, amount)
      stats <- AchievementRepository[F].getDrinkEventsForUser(userId)
      aggRes <- AggregateRepository[F].load(UserIdentifier(userId))
      (userAgg, _) = aggRes
      reachableAchievements = AchievementDefinitions.eventBaseAchievements
        .filter(achievementDef => userAgg.achievements.contains(achievementDef.achievement.id))

      achievementsToBeRemoved = if (stats.nonEmpty) reachableAchievements.filterNot(_.predicate(stats))
      else reachableAchievements
      achievementCommands = achievementsToBeRemoved.map(ach => RemoveAchievement(userId, ach.achievement.id))
      _ <- Traverse[List].sequence(achievementCommands.map(CommandProcessor[F].process[User](_)))
    } yield ()

  }

}
