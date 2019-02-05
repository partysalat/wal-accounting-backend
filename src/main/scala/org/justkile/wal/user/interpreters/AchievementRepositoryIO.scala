package org.justkile.wal.user.interpreters

import java.time.LocalDateTime

import cats.effect.IO
import doobie.implicits._
import doobie.util.meta.{Meta, MetaInstances}
import org.justkile.wal.db.Database
import org.justkile.wal.user.algebras.AchievementRepository
import org.justkile.wal.user.domain.UserDrinkEvent
import org.justkile.wal.utils.Done

object AchievementRepositoryIO extends MetaInstances {
  implicit val DateTimeMeta: Meta[LocalDateTime] = Meta[java.sql.Timestamp].xmap(
    ts => ts.toLocalDateTime,
    dt => java.sql.Timestamp.valueOf(dt)
  )
  implicit def achievementRepoInterpreter: AchievementRepository[IO] = new AchievementRepository[IO] {
    override def saveUserStats(userId: String, drinkId: Int, amount: Int, createdAt: LocalDateTime): IO[Option[Done]] =
      sql"INSERT INTO achievement_user_stats (userId, drinkId, amount, createdAt) VALUES ($userId,$drinkId, $amount, $createdAt)".update
        .withUniqueGeneratedKeys[Int]("id")
        .attemptSql
        .map(_.toOption.map(_ => Done))
        .transact(Database.xa)

    override def getDrinkEventsForUser(userId: String): IO[List[UserDrinkEvent]] =
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
        .query[UserDrinkEvent]
        .to[List]
        .transact(Database.xa)

    def subtractUserStats(userId: String, drinkId: Int, amount: Int): IO[Int] =
      sql"""DELETE FROM achievement_user_stats
           where USERID=$userId AND DRINKID=$drinkId AND amount=$amount LIMIT 1
         """.update.run
        .transact(Database.xa)

  }
}
