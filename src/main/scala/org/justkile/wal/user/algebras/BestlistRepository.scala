package org.justkile.wal.user.algebras

import org.justkile.wal.user.domain.BestlistUserStats
import org.justkile.wal.utils.Done

trait BestlistRepository[F[_]] {
  def initUser(userId: String): F[Option[Done]]
  def addDrinkNews(userId: String, drinkId: Int, amount: Int): F[Option[Done]]
  def removeDrinkNews(userId: String, drinkId: Int, amount: Int): F[Option[Done]]
  def addAchievement(userId: String, achievementId: Int): F[Option[Done]]
  def removeAchievement(userId: String, achievementId: Int): F[Option[Done]]

  def getStats(): F[List[BestlistUserStats]]
}
object BestlistRepository {
  def apply[F[_]: BestlistRepository]: BestlistRepository[F] = implicitly
}
