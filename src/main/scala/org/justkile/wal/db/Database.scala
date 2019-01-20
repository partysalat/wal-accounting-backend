package org.justkile.wal.db

import cats.effect.IO
import cats.implicits._
import doobie.implicits._
import doobie.util.transactor.Transactor
import org.justkile.wal.drinks.domain.{Drink, DrinkType}

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
        createdAt DATETIME NOT NULL
    )
    """.update.run,
    sql"""
      CREATE TABLE IF NOT EXISTS achievements (
        id          INT AUTO_INCREMENT PRIMARY KEY,
        name        VARCHAR NOT NULL,
        description VARCHAR NOT NULL
    )
    """.update.run
  ).traverse_(_.transact(xa))

  private val drinks = List(
    Drink(1, "Radeberger", DrinkType.BEER),
    Drink(2, "Long Island Iced tea", DrinkType.COCKTAIL)
  ).map(drink => {
    sql"""MERGE INTO drinks
          KEY(id)
          VALUES  (${drink.id}, ${drink.name}, ${drink.`type`})
      """.update.run
  })
//  private val achievements =  List()
  val insertions: IO[Unit] = List(drinks).flatten
    .traverse_(_.transact(xa))
}
