package org.justkile.wal.user

import org.justkile.wal.event_sourcing.{Aggregate, AggregateIdentifier, Command, Event}
import java.util.UUID.randomUUID

object model {

  case class UserIdentifier(id: String, name: String) extends AggregateIdentifier[User] {
    override def idAsString: String = s"user-$id"

    override def initialState: User = User(id, name)
  }

  case class User(
                   id: String,
                   name: String
                 )

  //Commands
  case class CreateUserCommand(name: String, id:String = randomUUID().toString) extends Command[User] {
    override def getAggregateIdentifier: AggregateIdentifier[User] = UserIdentifier(id, name)
  }
  //Events
  case class UserCreated(id:String, name: String) extends Event

  implicit def Aggregate: Aggregate[User] = new Aggregate[User] {
    override def identifier(agg: User): AggregateIdentifier[User] = UserIdentifier(agg.id,agg.name)

    override def empty(id: AggregateIdentifier[User]): User = id.initialState

    override def applyEvent(agg: User)(event: Event): User = event match {
      case UserCreated(id, name) => User(id, name)
    }

    override def getEventsFromCommand(agg: User)(command: Command[User]): List[Event] = command match {
      case CreateUserCommand(name, id)=> List(UserCreated(id, name))
    }
  }
}
