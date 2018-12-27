package org.justkile.wal.user

import org.justkile.wal.event_sourcing.{Aggregate, AggregateIdentifier, Command, Event}


object model {

  case class User(
                   id: Int,
                   name: String)

  implicit def Aggregate[User] = new Aggregate[User] {
    override def identifier(agg: User): AggregateIdentifier[User] = ???

    override def empty(id: AggregateIdentifier[User]): User = ???

    override def applyEvent(agg: User)(event: Event): User = ???

    override def getEventsFromCommand(agg: User)(command: Command[User]): List[Event] = ???
  }
}
