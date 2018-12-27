package org.justkile.wal.event_sourcing

import cats.effect.IO
import cats.implicits._
import doobie.implicits._
import org.justkile.wal.db.Database
import org.justkile.wal.event_sourcing.algebras.EventStore
import org.justkile.wal.utils.DefaultSerializer

object EventStoreIO {

  case class EventEnvelope(identifier: String, sequence: Int, event: String)

  implicit def eventStore: EventStore[IO] = new EventStore[IO] {

    private def toEvent(eventEnvelopes: EventEnvelope):Event= DefaultSerializer.deserialise(eventEnvelopes.event).asInstanceOf[Event]
    private def eventToString(evt: Event):String= DefaultSerializer.serialise(evt)

    private def insert1(identifier: String, sequence: Int, event: Event) =
      sql"""INSERT INTO events (identifier, sequence, event)
             VALUES ($identifier, $sequence, ${eventToString(event)})
        """.update
        .withUniqueGeneratedKeys[Int]("id")
        .attemptSql

    override def appendEventsTo[A](aggregateIdentifier: AggregateIdentifier[A], evt: List[Event], numberOfEvents: Int): IO[Boolean] = {
      evt
        .traverse(item => insert1(aggregateIdentifier.idAsString, numberOfEvents, item))
        .map(_ => true)
        .transact(Database.xa)

    }



    override def loadEventsFor[A](aggregateIdentifier: AggregateIdentifier[A]): IO[List[Event]] = {
      sql"""
        SELECT identifier, sequence, event
        FROM events
        WHERE identifier = ${aggregateIdentifier.idAsString}
        ORDER BY sequence ASC
      """
        .query[EventEnvelope]
        .to[List]
        .transact(Database.xa)
        .map(_.map(toEvent))

    }
  }
}
