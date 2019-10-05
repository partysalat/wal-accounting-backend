package org.justkile.wal.projections

import java.time.LocalDateTime

import org.justkile.wal.projections.domain.UserDrinkEvent
import org.justkile.wal.utils.Done

trait AchievementRepository[F[_]] {
  def saveUserStats(userId: String,
                    drinkId: Int,
                    amount: Int,
                    createdAt: LocalDateTime = LocalDateTime.now()): F[Option[Done]]
  def subtractUserStats(userId: String, drinkId: Int, amount: Int): F[Int]
  def getDrinkEventsForUser(userId: String): F[List[UserDrinkEvent]]
}

object AchievementRepository {
  def apply[F[_]: AchievementRepository]: AchievementRepository[F] = implicitly
}
