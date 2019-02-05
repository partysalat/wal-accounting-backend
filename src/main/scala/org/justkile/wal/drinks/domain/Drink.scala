package org.justkile.wal.drinks.domain

import org.justkile.wal.drinks.domain.DrinkType.DrinkType
case class Drink(id: Int, name: String, `type`: DrinkType)
