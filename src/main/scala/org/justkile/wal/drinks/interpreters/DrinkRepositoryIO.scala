package org.justkile.wal.drinks.interpreters

import cats.effect.IO
import doobie.implicits._
import org.justkile.wal.db.Database
import org.justkile.wal.drinks.algebras.DrinkRepository
import org.justkile.wal.drinks.domain.Drink
import org.justkile.wal.drinks.domain.DrinkType._

object DrinkRepositoryIO {

  implicit def drinkRepository: DrinkRepository[IO] = new DrinkRepository[IO] {
    def addDrink(name: String, drinkType: DrinkType): IO[Option[Drink]] =
      sql"INSERT INTO drinks (drinkName, drinkType) VALUES ($name, $drinkType)".update
        .withUniqueGeneratedKeys[Int]("id")
        .attemptSql
        .map(_.toOption.map(id => Drink(id, name, drinkType)))
        .transact(Database.xa)

    def getDrinksForType(drinkType: DrinkType): IO[List[Drink]] =
      sql"""
        SELECT id, drinkName, drinkType
        FROM drinks
        WHERE drinkType = $drinkType
      """
        .query[Drink]
        .to[List]
        .transact(Database.xa)

  }

}
