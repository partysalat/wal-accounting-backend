package org.justkile.wal.user

import org.justkile.wal.event_sourcing.{Aggregate, AggregateIdentifier, Command, Event}
import java.util.UUID.randomUUID

object model {

  case class UserIdentifier(id: String) extends AggregateIdentifier[User] {
    override def idAsString: String = s"user-$id"

    override def initialState: User = User(id, None)
  }

  case class User(
                   id: String,
                   name: Option[String]
                 )

  //Commands
  case class CreateUserCommand(name: String, id:String = randomUUID().toString) extends Command[User] {
    override def getAggregateIdentifier: AggregateIdentifier[User] = UserIdentifier(id)
  }
  //Events
  case class UserCreated(id:String, name: String) extends Event

  implicit def Aggregate: Aggregate[User] = new Aggregate[User] {
    override def identifier(agg: User): AggregateIdentifier[User] = UserIdentifier(agg.id)

    override def empty(id: AggregateIdentifier[User]): User = id.initialState

    override def applyEvent(agg: User)(event: Event): User = event match {
      case UserCreated(id, name) => User(id, Some(name))
    }

    override def getEventsFromCommand(agg: User)(command: Command[User]): List[Event] = (command ,agg.name) match {
      case (CreateUserCommand(name, id), None) => List(UserCreated(id, name))
      case _ => List.empty
    }
  }
}
