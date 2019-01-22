package org.justkile.wal.user.events.achievements

import org.justkile.wal.event_sourcing.Event

case class Achievement(
    id: Int,
    name: String,
    description: String,
    imagePath: String
)
case class AchievementDefinition(achievement: Achievement, predicate: List[Event] => Boolean)
object AchievementDefinitions {
  val list = List(
    )

}
