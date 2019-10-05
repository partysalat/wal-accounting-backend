package org.justkile.wal.core.drinks.domain

import org.justkile.wal.core.drinks.domain.DrinkType.DrinkType
case class Drink(id: Int, name: String, `type`: DrinkType)
