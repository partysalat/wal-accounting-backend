package org.justkile.wal.db

import cats.effect.IO
import cats.implicits._
import doobie.implicits._
import doobie.util.transactor.Transactor
import org.justkile.wal.core.achievements.AchievementDefinitions
import org.justkile.wal.core.drinks.domain.{Drink, DrinkType}

object Database {
  val xa: Transactor[IO] = Transactor.fromDriverManager[IO](
    "org.h2.Driver",
    "jdbc:h2:file:~/db.wal;DB_CLOSE_DELAY=-1",
//    "jdbc:h2:~/db.h2.wal;DB_CLOSE_DELAY=-1",
    "sa",
    ""
  )

  val schemaDefinition: IO[Unit] = List(
    sql"""
      SET IGNORECASE TRUE
    """.update.run,
    sql"""
      CREATE TABLE IF NOT EXISTS events (
      id         INT AUTO_INCREMENT PRIMARY KEY,
      identifier VARCHAR,
      sequence   INT,
      event      BINARY
    )
    """.update.run,
    sql"""
      CREATE TABLE IF NOT EXISTS users (
      id       INT AUTO_INCREMENT PRIMARY KEY,
      userId   VARCHAR UNIQUE NOT NULL,
      name     VARCHAR UNIQUE
    )
    """.update.run,
    sql"""
      CREATE TABLE IF NOT EXISTS drinks (
        id        INT AUTO_INCREMENT PRIMARY KEY,
        drinkName VARCHAR NOT NULL,
        drinkType VARCHAR NOT NULL
    )
    """.update.run,
    sql"""
      CREATE TABLE IF NOT EXISTS news (
        id        INT AUTO_INCREMENT PRIMARY KEY,
        newsType VARCHAR NOT NULL,
        userId VARCHAR NOT NULL,
        amount INT NOT NULL,
        referenceId INT NOT NULL,
        createdAt TIMESTAMP NOT NULL
    )
    """.update.run,
    sql"""
      CREATE TABLE IF NOT EXISTS achievement_user_stats (
        id INT AUTO_INCREMENT PRIMARY KEY,
        userId VARCHAR NOT NULL,
        drinkId INT NOT NULL,
        amount INT NOT NULL,
        createdAt TIMESTAMP NOT NULL
    )
    """.update.run,
    sql"""
      CREATE TABLE IF NOT EXISTS bestlist_user_stats (
        id INT AUTO_INCREMENT PRIMARY KEY,
        userId VARCHAR NOT NULL,
        beerCount INT NOT NULL DEFAULT 0,
        cocktailCount INT NOT NULL DEFAULT 0,
        softdrinkCount INT NOT NULL DEFAULT 0,
        shotCount INT NOT NULL DEFAULT 0,
        spaceInvadersScore BIGINT NOT NULL DEFAULT 0
    )
    """.update.run,
    sql"""
      CREATE TABLE IF NOT EXISTS bestlist_user_achievements (
        id INT AUTO_INCREMENT PRIMARY KEY,
        userId VARCHAR NOT NULL,
        achievementId INT NOT NULL
    )
    """.update.run,
    sql"""
      CREATE TABLE IF NOT EXISTS achievements (
        id          INT AUTO_INCREMENT PRIMARY KEY,
        name        VARCHAR NOT NULL,
        imagePath   VARCHAR NOT NULL,
        description VARCHAR NOT NULL
    )
    """.update.run
  ).traverse_(_.transact(xa))
}
