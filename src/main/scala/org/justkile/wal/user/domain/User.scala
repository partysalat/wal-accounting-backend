package org.justkile.wal.user.domain

import java.util.UUID.randomUUID

import org.justkile.wal.event_sourcing.{Aggregate, AggregateIdentifier, Command, Event}

case class User(
    id: String,
    name: Option[String]
    //      achievements: List[Achievement]
)

object User {

  case class UserIdentifier(id: String) extends AggregateIdentifier[User] {
    override def idAsString: String = s"user-$id"

    override def initialState: User = User(id, None)
  }

  //Commands
  case class CreateUserCommand(name: String, id: String = randomUUID().toString) extends Command[User] {
    override def getAggregateIdentifier: AggregateIdentifier[User] = UserIdentifier(id)
  }

  case class AddUserDrinkCommand(userId: String, drinkId: Int, amount: Int) extends Command[User] {
    override def getAggregateIdentifier: AggregateIdentifier[User] = UserIdentifier(userId)
  }
  case class RemoveUserDrinkCommand(userId: String, newsId: Int) extends Command[User] {
    override def getAggregateIdentifier: AggregateIdentifier[User] = UserIdentifier(userId)
  }

  //Events
  case class UserCreated(id: String, name: String) extends Event
  case class UserDrinkAdded(userId: String, drinkId: Int, amount: Int) extends Event
  case class UserDrinkRemoved(userId: String, newsId: Int) extends Event

  implicit def userAggregate: Aggregate[User] = new Aggregate[User] {
    override def identifier(agg: User): AggregateIdentifier[User] = UserIdentifier(agg.id)

    override def empty(id: AggregateIdentifier[User]): User = id.initialState

    override def applyEvent(agg: User)(event: Event): User = event match {
      case UserCreated(id, name) => User(id, Some(name))
      case UserDrinkAdded(_, _, _) => agg
      case UserDrinkRemoved(_, _) => agg
    }

    override def getEventsFromCommand(agg: User)(command: Command[User]): List[Event] = command match {
      case CreateUserCommand(name, id) if agg.name.isEmpty => List(UserCreated(id, name))
      case AddUserDrinkCommand(userId, drinkId, amount) => List(UserDrinkAdded(userId, drinkId, amount))
      case RemoveUserDrinkCommand(userId, newsId) => List(UserDrinkRemoved(userId, newsId))
      case _ => List.empty
    }
  }
}
