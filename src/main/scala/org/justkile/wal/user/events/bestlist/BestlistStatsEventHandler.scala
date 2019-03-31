package org.justkile.wal.user.events.bestlist

import cats.Applicative
import cats.effect.Sync
import cats.implicits._
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.event_sourcing.Event
import org.justkile.wal.event_sourcing.event_bus.EventBus.EventHandler
import org.justkile.wal.user.algebras.{BestlistRepository, UserRepository}
import org.justkile.wal.user.domain.User.{
  AchievementGained,
  AchievementRemoved,
  UserCreated,
  UserDrinkAdded,
  UserDrinkRemoved
}
import org.justkile.wal.utils.Done

class BestlistStatsEventHandler[F[_]: Sync: Logger: BestlistRepository: Applicative] extends EventHandler[F, Event] {

  def handle(event: Event): F[Done] =
    for {
      _ <- event match {
        case userCreated: UserCreated => handleUserCreated(userCreated)
        case drinkAdded: UserDrinkAdded => handleUserDrinkAdded(drinkAdded)
        case achievementGained: AchievementGained => handleAchievementGained(achievementGained)
        case achievementRemoved: AchievementRemoved => handleAchievementRemoved(achievementRemoved)
        case drinkRemoved: UserDrinkRemoved => handleDrinkRemoved(drinkRemoved)
        case _ => Applicative[F].pure(Done)
      }
    } yield Done

  private def handleUserCreated(userCreated: UserCreated) =
    for {
      _ <- BestlistRepository[F].initUser(userCreated.id)
    } yield Done
  private def handleDrinkRemoved(drinkRemoved: UserDrinkRemoved) =
    for {
      _ <- BestlistRepository[F].removeDrinkNews(
        drinkRemoved.userId,
        drinkRemoved.drinkId,
        drinkRemoved.amount
      )
    } yield Done
  private def handleAchievementRemoved(achievementRemoved: AchievementRemoved) =
    for {
      _ <- BestlistRepository[F].removeAchievement(
        achievementRemoved.userId,
        achievementRemoved.achievementId
      )
    } yield Done

  private def handleUserDrinkAdded(drinkAdded: UserDrinkAdded) =
    for {
      _ <- BestlistRepository[F].addDrinkNews(drinkAdded.userId, drinkAdded.drinkId, drinkAdded.amount)
    } yield Done

  private def handleAchievementGained(achievementGained: AchievementGained) =
    for {
      _ <- BestlistRepository[F].addAchievement(achievementGained.userId, achievementGained.achievementId)
    } yield Done

}
