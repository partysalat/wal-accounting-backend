package org.justkile.wal.user.domain

import java.time.LocalDateTime

import org.justkile.wal.drinks.domain.Drink

case class UserDrinkEvent(id: Int, userId: String, drinkId: Int, amount: Int, createdAt: LocalDateTime, drink: Drink)
