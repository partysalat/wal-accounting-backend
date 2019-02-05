package org.justkile.wal.db

import cats.effect.IO
import cats.implicits._
import doobie.implicits._
import doobie.util.transactor.Transactor
import org.justkile.wal.drinks.domain.{Drink, DrinkType}
import org.justkile.wal.user.events.achievements.AchievementDefinitions

object Database {
  val xa: Transactor[IO] = Transactor.fromDriverManager[IO](
    "org.h2.Driver",
    "jdbc:h2:./db.h2:test;DB_CLOSE_DELAY=-1",
    "sa",
    ""
  )

  val schemaDefinition: IO[Unit] = List(
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
        shotCount INT NOT NULL DEFAULT 0
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
  import DrinkType._
  private val drinks = List(
    Drink(1, "Heineken", BEER),
    Drink(2, "Herrengedeck", BEER),
    Drink(3, "Damengedeck", BEER),
    Drink(4, "Mojito", COCKTAIL),
    Drink(5, "Deine Mudder", COCKTAIL),
    Drink(6, "Caipirinha", COCKTAIL),
    Drink(7, "Zombie", COCKTAIL),
    Drink(8, "Erdbeer Daiquiri", COCKTAIL),
    Drink(9, "Long Island Iced Tea", COCKTAIL),
    Drink(10, "Mai Tai", COCKTAIL),
    Drink(11, "Planters Punch", COCKTAIL),
    Drink(12, "Tequila Sunrise", COCKTAIL),
    Drink(13, "Wodka Sling", COCKTAIL),
    Drink(14, "Springtime Cooler", COCKTAIL),
    Drink(15, "Pineapple Fizz", COCKTAIL),
    Drink(16, "White Lady", COCKTAIL),
    Drink(17, "Tom Collins", COCKTAIL),
    Drink(18, "LeGurk", COCKTAIL),
    Drink(19, "Cuba Libre", COCKTAIL),
    Drink(20, "Gin Tonic", COCKTAIL),
    Drink(21, "Hugo", COCKTAIL),
    Drink(22, "Screwdriver", COCKTAIL),
    Drink(23, "Wodka Julep", COCKTAIL),
    Drink(24, "Sonstiger Cocktail", COCKTAIL),
    Drink(25, "Moscow Mule", COCKTAIL),
    Drink(26, "White Russian", COCKTAIL),
    Drink(27, "Sex on the Beach", COCKTAIL),
    Drink(28, "Cola Whisky", COCKTAIL),
    Drink(29, "Bloody Mary", COCKTAIL),
    Drink(30, "Margarita", COCKTAIL),
    Drink(31, "Berliner Luft", SHOT),
    Drink(32, "Wodka", SHOT),
    Drink(33, "Tequila", SHOT),
    Drink(34, "Polnische Rakede", SHOT),
    Drink(35, "Mexikaner", SHOT),
    Drink(36, "Cola", SOFTDRINK),
    Drink(37, "Fanta", SOFTDRINK),
    Drink(38, "Sprite", SOFTDRINK),
    Drink(39, "Mate", SOFTDRINK),
    Drink(40, "Wasser", SOFTDRINK),
    Drink(41, "Cocktail ohne alk", SOFTDRINK),
    Drink(42, "Orangensaft", SOFTDRINK),
    Drink(43, "Apfelsaft", SOFTDRINK),
    Drink(44, "Tomatensaft", SOFTDRINK),
    Drink(45, "Spezi", SOFTDRINK)
  ).map(drink => {
    sql"""MERGE INTO drinks
          KEY(id)
          VALUES  (${drink.id}, ${drink.name}, ${drink.`type`})
      """.update.run
  })
  private val achievements = AchievementDefinitions.eventBaseAchievements
    .map(_.achievement)
    .map(achievement => {
      sql"""MERGE INTO achievements
          KEY(id)
          VALUES  (${achievement.id}, ${achievement.name}, ${achievement.description}, ${achievement.imagePath})
      """.update.run
    })

  val insertions: IO[Unit] = List(drinks, achievements).flatten
    .traverse_(_.transact(xa))
}
