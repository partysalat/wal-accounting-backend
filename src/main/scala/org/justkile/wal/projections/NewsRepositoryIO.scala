package org.justkile.wal.projections

import java.time.LocalDateTime

import cats.effect.IO
import doobie.implicits._
import doobie.util.meta.Meta
import org.justkile.wal.db.Database
import org.justkile.wal.projections.domain._

object NewsRepositoryIO {

  import java.time.format.DateTimeFormatter

  val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  implicit val localDateTimeMeta: Meta[LocalDateTime] = Meta[String]
    .xmap((s: String) => LocalDateTime.parse(s, formatter), (d: LocalDateTime) => d.format(formatter))

  implicit def newsRepoInterpreter: NewsRepository[IO] = new NewsRepository[IO] {
    def addDrinkNews(userId: String, drinkId: Int, amount: Int, createdAt: LocalDateTime): IO[Option[News]] =
      sql"""
           INSERT INTO news (newsType, userId, amount, referenceId, createdAt)
           VALUES (${NewsType.DRINK}, $userId, $amount, $drinkId, $createdAt)""".update
        .withUniqueGeneratedKeys[Int]("id")
        .attemptSql
        .map(_.toOption.map(id => News(id, NewsType.DRINK, userId, amount, drinkId, createdAt)))
        .transact(Database.xa)

    override def getNews(skip: Int, pageSize: Int): IO[List[JoinedNews]] =
      (newsWithDrinksOrAchievementsQuery ++ fr"""
         ORDER BY n.createdAt DESC, n.id
         LIMIT $pageSize OFFSET $skip
         """)
        .query[(News, UserProjection, Option[DrinkPayload], Option[AchievementPayload])]
        .to[List]
        .transact(Database.xa)
        .map(resultList => resultList.map(JoinedNews.from))

    def getDrinkNews(skip: Int, pageSize: Int): IO[List[JoinedNews]] =
      (onlyDrinkNewsQuery ++ fr"""
         ORDER BY n.createdAt DESC, n.id
         LIMIT $pageSize OFFSET $skip
         """)
        .query[(News, UserProjection, DrinkPayload)]
        .to[List]
        .transact(Database.xa)
        .map(_.map(res => domain.JoinedNews(res._1, res._2, res._3)))

    def removeNews(userId: String, newsId: Int): IO[Int] =
      sql"DELETE FROM NEWS where id=$newsId".update.run
        .transact(Database.xa)

    override def addAchievement(userId: String, achievementId: Int, createdAt: LocalDateTime): IO[Option[News]] =
      sql"""
           INSERT INTO news (newsType, userId, amount, referenceId, createdAt)
           VALUES (${NewsType.ACHIEVEMENT}, $userId, 1, $achievementId, $createdAt)""".update
        .withUniqueGeneratedKeys[Int]("id")
        .attemptSql
        .map(_.toOption.map(id => News(id, NewsType.DRINK, userId, 1, achievementId, createdAt)))
        .transact(Database.xa)

    def removeAchievement(userId: String, achievementId: Int): IO[Int] =
      sql"DELETE FROM NEWS where USERID=$userId AND REFERENCEID=$achievementId".update.run
        .transact(Database.xa)
    def getNewsItem(newsId: Int): IO[JoinedNews] =
      (newsWithDrinksOrAchievementsQuery ++ fr"""
         WHERE n.id = $newsId
         ORDER BY createdAt DESC, n.id
         """)
        .query[(News, UserProjection, Option[DrinkPayload], Option[AchievementPayload])]
        .unique
        .transact(Database.xa)
        .map(JoinedNews.from)

  }
  private val onlyDrinkNewsQuery = {
    fr"""
        SELECT n.id,
                n.newsType,
                n.userId,
                n.amount,
                n.referenceId,
                n.createdAt,

                u.id,
                u.userId,
                u.name,

                d.id,
                d.drinkName,
                d.drinkType

         FROM NEWS n
         LEFT JOIN USERS u ON n.userId = u.userId
         LEFT JOIN DRINKS d ON n.REFERENCEID = d.id
         WHERE n.newsType = 'DRINK'
        """
  }
  private val newsWithDrinksOrAchievementsQuery = {
    fr"""
          SELECT n.id,
                n.newsType,
                n.userId,
                n.amount,
                n.referenceId,
                n.createdAt,

                u.id,
                u.userId,
                u.name,

                d.id,
                d.drinkName,
                d.drinkType,

                a.id,
                a.name,
                a.description,
                a.imagePath

         FROM NEWS n
         LEFT JOIN USERS u ON n.userId = u.userId
         LEFT JOIN DRINKS d ON
             CASE WHEN n.newsType = 'DRINK' AND n.REFERENCEID = d.id THEN 1
             ELSE 0 END = 1
         LEFT JOIN ACHIEVEMENTS a ON
             CASE WHEN n.newsType = 'ACHIEVEMENT' AND n.REFERENCEID = a.id THEN 1
             ELSE 0 END = 1
          """
  }

}
