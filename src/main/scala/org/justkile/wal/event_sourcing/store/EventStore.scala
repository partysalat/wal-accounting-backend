package org.justkile.wal.event_sourcing.store

import org.justkile.wal.event_sourcing.{AggregateIdentifier, Event}

trait EventStore[F[_]] {

  def appendEventsTo[A](aggregateIdentifier: AggregateIdentifier[A], evt: List[Event], numberOfEvents: Int): F[Boolean]

  def loadEventsFor[A](aggregateIdentifier: AggregateIdentifier[A]): F[List[Event]]
}

object EventStore {
  def apply[F[_]: EventStore]: EventStore[F] = implicitly
}
