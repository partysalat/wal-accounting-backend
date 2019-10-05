package org.justkile.wal.domain

import org.justkile.wal.event_sourcing.Event

object UserEvents {
  case class UserCreated(id: String, name: String) extends Event
  case class UserDrinkAdded(userId: String, drinkId: Int, amount: Int) extends Event
  case class UserDrinkRemoved(userId: String, newsId: Int, drinkId: Int, amount: Int) extends Event
  case class AchievementGained(userId: String, achievementId: Int) extends Event
  case class AchievementRemoved(userId: String, achievementId: Int) extends Event
  case class ScoreSet(userId: String, score: Long) extends Event

}
