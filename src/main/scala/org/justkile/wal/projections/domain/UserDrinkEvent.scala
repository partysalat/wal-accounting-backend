package org.justkile.wal.projections.domain

import java.time.LocalDateTime

import org.justkile.wal.core.drinks.domain.Drink

case class UserDrinkEvent(id: Int, userId: String, drinkId: Int, amount: Int, createdAt: LocalDateTime, drink: Drink)
