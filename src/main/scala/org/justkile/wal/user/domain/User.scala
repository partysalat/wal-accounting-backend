package org.justkile.wal.user.domain

import java.util.UUID.randomUUID

import org.justkile.wal.event_sourcing.{Aggregate, AggregateIdentifier, Command, Event}
import org.justkile.wal.user.domain.NewsType.NewsType
import org.justkile.wal.user.domain.User.AchievementId

case class User(
    id: String,
    name: Option[String],
    achievements: List[AchievementId]
)

object User {
  type AchievementId = Int
  case class UserIdentifier(id: String) extends AggregateIdentifier[User] {
    override def idAsString: String = s"user-$id"

    override def initialState: User = User(id, None, List.empty)
  }

  //Commands
  case class CreateUserCommand(name: String, id: String = randomUUID().toString) extends Command[User] {
    override def getAggregateIdentifier: AggregateIdentifier[User] = UserIdentifier(id)
  }

  case class AddUserDrinkCommand(userId: String, drinkId: Int, amount: Int) extends Command[User] {
    override def getAggregateIdentifier: AggregateIdentifier[User] = UserIdentifier(userId)
  }
  //TODO: Handle Rollback Case for ALL Event handlers!
  case class RemoveUserDrinkCommand(userId: String, newsId: Int, drinkId: Int, amount: Int) extends Command[User] {
    override def getAggregateIdentifier: AggregateIdentifier[User] = UserIdentifier(userId)
  }
  case class GainAchievement(userId: String, achievementId: Int) extends Command[User] {
    override def getAggregateIdentifier: AggregateIdentifier[User] = UserIdentifier(userId)
  }
  case class RemoveAchievement(userId: String, achievementId: Int) extends Command[User] {
    override def getAggregateIdentifier: AggregateIdentifier[User] = UserIdentifier(userId)
  }

  //Events
  case class UserCreated(id: String, name: String) extends Event
  case class UserDrinkAdded(userId: String, drinkId: Int, amount: Int) extends Event
  case class UserDrinkRemoved(userId: String, newsId: Int, drinkId: Int, amount: Int) extends Event
  case class AchievementGained(userId: String, achievementId: Int) extends Event
  case class AchievementRemoved(userId: String, achievementId: Int) extends Event

  implicit def userAggregate: Aggregate[User] = new Aggregate[User] {
    override def identifier(agg: User): AggregateIdentifier[User] = UserIdentifier(agg.id)

    override def empty(id: AggregateIdentifier[User]): User = id.initialState

    override def applyEvent(agg: User)(event: Event): User = event match {
      case UserCreated(id, name) => User(id, Some(name), List.empty)
      case UserDrinkAdded(_, _, _) => agg
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
      case _ => List.empty
    }
  }
}
