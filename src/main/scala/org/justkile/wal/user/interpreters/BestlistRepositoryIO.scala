package org.justkile.wal.user.interpreters

import cats.effect.IO
import doobie.implicits._
import doobie.util.meta.MetaInstances
import org.justkile.wal.db.Database
import org.justkile.wal.drinks.domain.{Drink, DrinkType}
import org.justkile.wal.user.algebras.BestlistRepository
import org.justkile.wal.user.domain.{BestlistUserStats, UserProjection}
import org.justkile.wal.user.events.achievements.Achievement
import org.justkile.wal.utils.Done

object BestlistRepositoryIO extends MetaInstances {
  implicit def bestlistRepoInterpreter: BestlistRepository[IO] = new BestlistRepository[IO] {
    private case class BestlistDrinkStats(id: Int,
                                          userId: String,
                                          beerCount: Int,
                                          cocktailCount: Int,
                                          softdrinkCount: Int,
                                          shotCount: Int,
                                          user: UserProjection)

    def initUser(userId: String): IO[Option[Done]] =
      sql"INSERT INTO bestlist_user_stats (userId) VALUES ($userId)".update
        .withUniqueGeneratedKeys[Int]("id")
        .attemptSql
        .map(_.toOption.map(_ => Done))
        .transact(Database.xa)

    private def getDrinkFromId(drinkId: Int): IO[Drink] = {
      sql"""
        SELECT id, drinkName, drinkType
        FROM drinks
        WHERE id = $drinkId
      """
        .query[Drink]
        .unique
        .transact(Database.xa)
    }
    override def addDrinkNews(userId: String, drinkId: Int, amount: Int): IO[Option[Done]] = {
      for {
        drink <- getDrinkFromId(drinkId)
        query = drink.`type` match {
          case DrinkType.BEER =>
            sql"UPDATE bestlist_user_stats SET beerCount = beerCount + $amount WHERE userId = $userId"
          case DrinkType.COCKTAIL =>
            sql"UPDATE bestlist_user_stats SET cocktailCount = cocktailCount + $amount WHERE userId = $userId"
          case DrinkType.SOFTDRINK =>
            sql"UPDATE bestlist_user_stats SET softdrinkCount = softdrinkCount + $amount WHERE userId = $userId"
          case _ => sql"UPDATE bestlist_user_stats SET shotCount = shotCount + $amount WHERE userId = $userId"
        }
        res <- query.update.run.transact(Database.xa)
      } yield if (res > 0) Some(Done) else None

    }

    override def removeDrinkNews(userId: String, drinkId: Int, amount: Int): IO[Option[Done]] = ???

    override def addAchievement(userId: String, achievementId: Int): IO[Option[Done]] =
      sql"INSERT INTO bestlist_user_achievements (userId, achievementId) VALUES ($userId, $achievementId)".update
        .withUniqueGeneratedKeys[Int]("id")
        .attemptSql
        .map(_.toOption.map(_ => Done))
        .transact(Database.xa)

    override def removeAchievement(userId: String, achievementId: Int): IO[Option[Done]] = ???

    override def getStats(): IO[List[BestlistUserStats]] = {
      for {
        things <- sql"""
         SELECT s.id,
                s.userId,
                s.beerCount,
                s.cocktailCount,
                s.softdrinkCount,
                s.shotCount,

                u.id,
                u.userId,
                u.name
         FROM bestlist_user_stats s
         LEFT JOIN USERS u ON s.userId = u.userId
         """
          .query[BestlistDrinkStats]
          .to[List]
          .transact(Database.xa)
        achievements <- sql"""
          SELECT
           ba.userId,

           a.id,
           a.name,
           a.imagePath,
           a.description
          FROM bestlist_user_achievements ba
          LEFT JOIN ACHIEVEMENTS a ON ba.ACHIEVEMENTID = a.ID
              """.query[(String, Achievement)].to[List].transact(Database.xa)
        groupedAchievements = achievements.groupBy(_._1)
        stats = things.map { drinkStats =>
          import drinkStats._
          BestlistUserStats(id,
                            userId,
                            beerCount,
                            cocktailCount,
                            softdrinkCount,
                            shotCount,
                            user,
                            groupedAchievements.getOrElse(userId, List.empty).map(_._2))
        }
      } yield stats

//      res.transact(Database.xa)
    }

  }
}
