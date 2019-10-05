package org.justkile.wal.core.drinks

import cats.effect.IO
import org.justkile.wal.core.drinks.domain.Drink
import org.justkile.wal.core.drinks.domain.DrinkType.DrinkType
import org.justkile.wal.db.Database
import doobie.implicits._

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
        ORDER BY DRINKNAME ASC
      """
        .query[Drink]
        .to[List]
        .transact(Database.xa)

  }

}
