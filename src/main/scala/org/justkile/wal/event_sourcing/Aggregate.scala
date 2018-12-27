package org.justkile.wal.event_sourcing

import cats.data.State
import simulacrum.typeclass

trait Event

trait AggregateIdentifier[A] {
  def idAsString: String
  // should be at the aggregate itself. feel free to try yourself (bring some time)
  def initialState: A
}

@typeclass
trait Aggregate[A] {
  def identifier(agg: A): AggregateIdentifier[A]
  def empty(id: AggregateIdentifier[A]): A

  def emptyState: State[A, List[Event]] = State(s => (s, List.empty))

  def source(id: AggregateIdentifier[A], events: List[Event]): A =
    events.foldLeft(empty(id))((acc, item) => applyEvent(acc)(item))

  def applyEvent(agg: A)(event: Event): A

  def getEventsFromCommand(agg: A)(command: Command[A]): List[Event]

  def processCommand(command: Command[A]): State[A, List[Event]] = State(s => {
    val events = getEventsFromCommand(s)(command)
    (events.foldLeft(s)(applyEvent(_)(_)), events)
  })

}
