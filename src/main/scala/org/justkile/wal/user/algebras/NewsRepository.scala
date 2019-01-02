package org.justkile.wal.user.algebras

import org.justkile.wal.user.domain.{JoinedNews, News}

trait NewsRepository[F[_]] {
  def addDrinkNews(userId: String, drinkId: Int, amount: Int): F[Option[News]]
  def getNews(skip: Int): F[List[JoinedNews]]
}
object NewsRepository {
  def apply[F[_]: NewsRepository]: NewsRepository[F] = implicitly
}
