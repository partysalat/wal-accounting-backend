package org.justkile.wal.drinks.algebras

import org.justkile.wal.drinks.domain.Drink
import org.justkile.wal.drinks.domain.DrinkType.DrinkType

trait DrinkRepository[F[_]] {
  def addDrink(name: String, `type`: DrinkType): F[Option[Drink]]
  def getDrinksForType(`type`: DrinkType): F[List[Drink]]
}

object DrinkRepository {
  def apply[F[_]: DrinkRepository]: DrinkRepository[F] = implicitly
}
