package org.justkile.wal.domain

import org.justkile.wal.domain.User.AchievementId
import org.justkile.wal.domain.UserCommands._
import org.justkile.wal.domain.UserEvents._
import org.justkile.wal.event_sourcing.{Aggregate, AggregateIdentifier, Command, Event}

case class User(
    id: String,
    name: Option[String],
    achievements: List[AchievementId],
    score: Long
)

object User {
  type AchievementId = Int
  case class UserIdentifier(id: String) extends AggregateIdentifier[User] {
    override def idAsString: String = s"user-$id"

    override def initialState: User = User(id, None, List.empty, 0)
  }

  implicit def userAggregate: Aggregate[User] = new Aggregate[User] {
    override def identifier(agg: User): AggregateIdentifier[User] = UserIdentifier(agg.id)

    override def empty(id: AggregateIdentifier[User]): User = id.initialState

    override def applyEvent(agg: User)(event: Event): User = event match {
      case UserCreated(id, name) => User(id, Some(name), List.empty, 0)
      case UserDrinkAdded(_, _, _) => agg
      case ScoreSet(_, score) => agg.copy(score = score)
      case UserDrinkRemoved(_, _, _, _) => agg
      case AchievementGained(_, achievementId) => agg.copy(achievements = achievementId :: agg.achievements)
      case AchievementRemoved(_, achievementId) =>
        agg.copy(achievements = agg.achievements.filterNot(_ == achievementId))
    }

    override def getEventsFromCommand(agg: User)(command: Command[User]): List[Event] = command match {
      case CreateUserCommand(name, id) if agg.name.isEmpty => List(UserCreated(id, name))
      case AddUserDrinkCommand(userId, drinkId, amount) if agg.name.isDefined =>
        List(UserDrinkAdded(userId, drinkId, amount))
      case RemoveUserDrinkCommand(userId, newsId, drinkId, amount) if agg.name.isDefined =>
        List(UserDrinkRemoved(userId, newsId, drinkId, amount))
      case GainAchievement(userId, achievementId) if !agg.achievements.contains(achievementId) && agg.name.isDefined =>
        List(AchievementGained(userId, achievementId))
      case RemoveAchievement(userId, achievementId) if agg.achievements.contains(achievementId) && agg.name.isDefined =>
        List(AchievementRemoved(userId, achievementId))
      case SetUserScoreCommand(userId, score) if score > agg.score =>
        List(ScoreSet(userId, score))
      case _ => List.empty
    }
  }
}
