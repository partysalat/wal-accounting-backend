package org.justkile.wal.user.interpreters

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import cats.effect.IO
import doobie.implicits._
import doobie.util.meta.Meta
import doobie.util.meta.Meta._
import org.justkile.wal.db.Database
import org.justkile.wal.user.algebras.AchievementRepository
import org.justkile.wal.user.domain.UserDrinkEvents
import org.justkile.wal.utils.Done

object AchievementRepositoryIO {
  val FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
  implicit val drinkTypeMeta: Meta[LocalDateTime] = Meta[String]
    .xmap((s: String) => LocalDateTime.parse(s, FORMATTER), (d: LocalDateTime) => d.toString)

  implicit def achievementRepoInterpreter: AchievementRepository[IO] = new AchievementRepository[IO] {
    override def saveUserStats(userId: String, drinkId: Int, amount: Int, createdAt: LocalDateTime): IO[Option[Done]] =
      sql"INSERT INTO achievement_user_stats (userId, drinkId, amount, createdAt) VALUES ($userId,$drinkId, $amount, $createdAt)".update
        .withUniqueGeneratedKeys[Int]("id")
        .attemptSql
        .map(_.toOption.map(_ => Done))
        .transact(Database.xa)

    override def getStatsForUser(userId: String): IO[List[UserDrinkEvents]] =
      sql"""
         SELECT n.id,
                n.userId,
                n.drinkId,
                n.amount,
                n.createdAt,

                d.id,
                d.drinkName,
                d.drinkType
         FROM achievement_user_stats n
         LEFT JOIN DRINKS d ON n.drinkId = d.id
         ORDER BY createdAt ASC
         """
        .query[UserDrinkEvents]
        .to[List]
        .transact(Database.xa)
  }
}
