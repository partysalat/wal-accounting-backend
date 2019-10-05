package org.justkile.wal.core.drinks

import org.justkile.wal.core.drinks.domain.Drink
import org.justkile.wal.core.drinks.domain.DrinkType.DrinkType

trait DrinkRepository[F[_]] {
  def addDrink(name: String, `type`: DrinkType): F[Option[Drink]]
  def getDrinksForType(`type`: DrinkType): F[List[Drink]]
}

object DrinkRepository {
  def apply[F[_]: DrinkRepository]: DrinkRepository[F] = implicitly
}
