package org.justkile.wal.event_sourcing

import cats.effect.Sync
import cats.syntax.functor._
import org.justkile.wal.event_sourcing.store.EventStore

class AggregateRepository[F[_]: Sync: EventStore] {

  type NumberOfEvents = Int

  def persist[A: Aggregate](aggregateIdentifier: AggregateIdentifier[A],
                            events: List[Event],
                            nbrOfExistingEvents: Int): F[Boolean] =
    EventStore[F].appendEventsTo(aggregateIdentifier, events, nbrOfExistingEvents)

  def load[A: Aggregate](aggregateIdentifier: AggregateIdentifier[A]): F[(A, NumberOfEvents)] = {
    for {
      events <- EventStore[F].loadEventsFor[A](aggregateIdentifier)
      aggregate = Aggregate[A].source(aggregateIdentifier, events)
    } yield (aggregate, events.length)
  }

}

object AggregateRepository {
  def apply[F[_]: AggregateRepository]: AggregateRepository[F] = implicitly
}
