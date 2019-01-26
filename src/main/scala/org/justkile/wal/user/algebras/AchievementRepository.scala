package org.justkile.wal.user.algebras

import java.time.LocalDateTime

import org.justkile.wal.user.domain.UserDrinkEvent
import org.justkile.wal.utils.Done

trait AchievementRepository[F[_]] {
  def saveUserStats(userId: String,
                    drinkId: Int,
                    amount: Int,
                    createdAt: LocalDateTime = LocalDateTime.now()): F[Option[Done]]
  def getDrinkEventsForUser(userId: String): F[List[UserDrinkEvent]]
}

object AchievementRepository {
  def apply[F[_]: AchievementRepository]: AchievementRepository[F] = implicitly
}