package org.justkile.wal.user.algebras

import java.time.LocalDateTime

import org.justkile.wal.user.domain.{JoinedNews, News}

trait NewsRepository[F[_]] {
  def addDrinkNews(userId: String,
                   drinkId: Int,
                   amount: Int,
                   createdAt: LocalDateTime = LocalDateTime.now()): F[Option[News]]
  def removeNews(userId: String, newsId: Int): F[Int]
  def addAchievement(userId: String,
                     achievementId: Int,
                     createdAt: LocalDateTime = LocalDateTime.now()): F[Option[News]]
  def removeAchievement(userId: String, achievementId: Int): F[Int]
  def getNews(skip: Int, pageSize: Int): F[List[JoinedNews]]
  def getNewsItem(newsId: Int): F[JoinedNews]
}
object NewsRepository {
  def apply[F[_]: NewsRepository]: NewsRepository[F] = implicitly
}
