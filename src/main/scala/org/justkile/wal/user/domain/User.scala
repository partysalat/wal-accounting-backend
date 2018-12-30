package org.justkile.wal.user.domain

import org.justkile.wal.event_sourcing.{Aggregate, AggregateIdentifier, Command, Event}
import java.util.UUID.randomUUID

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

  //Events
  case class UserCreated(id: String, name: String) extends Event

  implicit def userAggregate: Aggregate[User] = new Aggregate[User] {
    override def identifier(agg: User): AggregateIdentifier[User] = UserIdentifier(agg.id)

    override def empty(id: AggregateIdentifier[User]): User = id.initialState

    override def applyEvent(agg: User)(event: Event): User = event match {
      case UserCreated(id, name) => User(id, Some(name))
    }

    override def getEventsFromCommand(agg: User)(command: Command[User]): List[Event] = (command, agg.name) match {
      case (CreateUserCommand(name, id), None) => List(UserCreated(id, name))
      case _ => List.empty
    }
  }
}
