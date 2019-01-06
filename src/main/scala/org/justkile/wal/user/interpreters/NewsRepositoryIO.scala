package org.justkile.wal.user.interpreters

import java.time.LocalDateTime

import cats.effect.IO
import doobie.implicits._
import doobie.util.meta.Meta
import doobie.util.meta.Meta._
import org.justkile.wal.db.Database
import org.justkile.wal.user.algebras.NewsRepository
import org.justkile.wal.user.domain._
object NewsRepositoryIO {
  implicit val drinkTypeMeta: Meta[LocalDateTime] = Meta[String]
    .xmap((s: String) => LocalDateTime.parse(s), (d: LocalDateTime) => d.toString)

  implicit def newsRepoInterpreter: NewsRepository[IO] = new NewsRepository[IO] {
    def addDrinkNews(userId: String, drinkId: Int, amount: Int, createdAt: LocalDateTime): IO[Option[News]] =
      sql"INSERT INTO news (newsType, userId, amount, referenceId, createdAt) VALUES (${NewsType.DRINK}, $userId, $amount, $drinkId, $createdAt)".update
        .withUniqueGeneratedKeys[Int]("id")
        .attemptSql
        .map(_.toOption.map(id => News(id, NewsType.DRINK, userId, amount, drinkId)))
        .transact(Database.xa)

    override def getNews(skip: Int, pageSize: Int): IO[List[JoinedNews]] =
      sql"""
         SELECT n.id,
                n.newsType,
                n.userId,
                n.amount,
                n.referenceId,
                u.id,
                u.userId,
                u.name,

                d.id,
                d.drinkName,
                d.drinkType,

                a.id,
                a.name
         FROM NEWS n
         LEFT JOIN USERS u ON n.userId = u.userId
         LEFT JOIN DRINKS d ON
             CASE WHEN n.newsType = 'DRINK' AND n.REFERENCEID = d.id THEN 1
             ELSE 0 END = 1
         LEFT JOIN ACHIEVEMENTS a ON
             CASE WHEN n.newsType = 'ACHIEVEMENT' AND n.REFERENCEID = a.id THEN 1
             ELSE 0 END = 1
         ORDER BY createdAt DESC
         LIMIT $pageSize OFFSET $skip
         """
        .query[(News, UserProjection, Option[DrinkPayload], Option[AchievementPayload])]
        .to[List]
        .transact(Database.xa)
        .map(
          _.map(
            res =>
              JoinedNews(res._1,
                         res._2,
                         List(res._3, res._4)
                           .find(_.isDefined)
                           .flatten
                           .get)))

    override def removeDrinkNews(userId: String, newsId: Int): IO[Int] =
      sql"DELETE FROM NEWS where id=$newsId".update.run
        .transact(Database.xa)

  }
}
