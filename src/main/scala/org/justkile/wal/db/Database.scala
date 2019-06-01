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
    "jdbc:h2:~/db.h2.wal;DB_CLOSE_DELAY=-1",
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
  import DrinkType._
  private val drinks = List(
    Drink(1, "Major Label", COCKTAIL),
    Drink(4, "KontinuumstransMojitor", COCKTAIL),
    Drink(5, "Dein Erzeuger", COCKTAIL),
    Drink(6, "Cassiopairinha", COCKTAIL),
    Drink(7, "Space Zombie", COCKTAIL),
    Drink(8, "Romulanisches Ale", COCKTAIL),
    Drink(9, "Deep Space 9 Iced Tea", COCKTAIL),
    Drink(10, "Mars Tai", COCKTAIL),
    Drink(11, "Nebulas Punch", COCKTAIL),
    Drink(12, "Galactic Sunrise", COCKTAIL),
    Drink(13, "Asgard Sling", COCKTAIL),
    Drink(14, "Solar Springtime Cooler", COCKTAIL),
    Drink(15, "Pineapple Star Fizz", COCKTAIL),
    Drink(16, "White Lady (Atom)", COCKTAIL),
    Drink(17, "Tom Collins böser Zwillingsbruder", COCKTAIL),
    Drink(18, "LeCosmoGurque", COCKTAIL),
    Drink(19, "Pluto Libre", COCKTAIL),
    Drink(20, "Victory Gin Tonic", COCKTAIL),
    Drink(21, "Klingonischer Blutwein", COCKTAIL),
    Drink(22, "Sonic Screwdriver", COCKTAIL),
    Drink(23, "Asgard Julep", COCKTAIL),
    Drink(25, "Mercury Mule", COCKTAIL),
    Drink(26, "White Glorb", COCKTAIL),
    Drink(27, "Comet Coitus", COCKTAIL),
    Drink(28, "Tschunk!", COCKTAIL),
    Drink(29, "Bloody Red Shirt", COCKTAIL),
    Drink(30, "Margarita 2000", COCKTAIL),
    Drink(31, "Berliner Luft", SHOT),
    Drink(32, "Wodka", SHOT),
    Drink(33, "Tequila", SHOT),
    Drink(34, "Polnische Rakede", SHOT),
    Drink(35, "Mexikaner", SHOT),
    Drink(35, "Whisky", SHOT),
    Drink(36, "Cola", SOFTDRINK),
    Drink(37, "Fanta", SOFTDRINK),
    Drink(38, "Sprite", SOFTDRINK),
    Drink(39, "Mate", SOFTDRINK),
    Drink(40, "Wasser", SOFTDRINK),
    Drink(41, "Orangensaft", SOFTDRINK),
    Drink(42, "Apfelsaft", SOFTDRINK),
    Drink(43, "Spezi", SOFTDRINK),
    Drink(44, "Mogh's Wine", SOFTDRINK),
    Drink(44, "Mogh's Gimlet", SOFTDRINK),
    Drink(45, "Watermelon Margarita 2000", SOFTDRINK),
    Drink(45, "Virgin Comet Coitus", SOFTDRINK),
    Drink(45, "KontinuumstransMockitor", SOFTDRINK),
    Drink(45, "Virgin Mercury Mule", SOFTDRINK),
    Drink(45, "Shirleys Ginger Rocket", SOFTDRINK)
  ).map(drink => {
    sql"""MERGE INTO drinks
          KEY(id)
          VALUES  (${drink.id}, ${drink.name}, ${drink.`type`})
      """.update.run
  })
  private val achievements =
    List(AchievementDefinitions.eventBaseAchievements, AchievementDefinitions.spaceInvadersScoreAchievements).flatten
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
