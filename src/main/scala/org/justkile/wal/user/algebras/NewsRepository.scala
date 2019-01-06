package org.justkile.wal.user.algebras

import java.time.LocalDateTime

import org.justkile.wal.user.domain.{JoinedNews, News}

trait NewsRepository[F[_]] {
  def addDrinkNews(userId: String,
                   drinkId: Int,
                   amount: Int,
                   createdAt: LocalDateTime = LocalDateTime.now()): F[Option[News]]
  def removeDrinkNews(userId: String, newsId: Int): F[Int]
  def getNews(skip: Int, pageSize: Int): F[List[JoinedNews]]
}
object NewsRepository {
  def apply[F[_]: NewsRepository]: NewsRepository[F] = implicitly
}
