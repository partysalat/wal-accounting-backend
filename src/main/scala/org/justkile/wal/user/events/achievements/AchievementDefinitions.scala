package org.justkile.wal.user.events.achievements

import org.justkile.wal.drinks.domain.DrinkType
import org.justkile.wal.user.domain.UserDrinkEvents

case class Achievement(
    id: Int,
    name: String,
    description: String,
    imagePath: String
)
case class AchievementDefinition(achievement: Achievement, predicate: List[UserDrinkEvents] => Boolean)
object AchievementDefinitions {
  val eventBaseAchievements = List(
    AchievementDefinition(
      Achievement(
        1,
        "Moe",
        "1 Bier bestellt",
        "/internal/assets/achievements/moe.png"
      ),
      (events: List[UserDrinkEvents]) => events.exists(_.drink.`type` == DrinkType.BEER)
    )
  )

}
