package org.justkile.wal.event_sourcing.store

import cats.Traverse
import cats.effect.IO
import cats.implicits._
import com.sksamuel.avro4s.{Decoder, Encoder}
import doobie.implicits._
import io.chrisdavenport.log4cats.Logger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import org.justkile.wal.db.Database
import org.justkile.wal.domain.UserEvents
import org.justkile.wal.domain.UserEvents.UserEvent
import org.justkile.wal.event_sourcing.{AggregateIdentifier, Event}
import org.justkile.wal.utils.AvroSerializer

object EventStoreIO {

  case class EventEnvelope(identifier: String, sequence: Int, event: Array[Byte])

  implicit def eventStore: EventStore[IO] = new EventStore[IO] {
    implicit def loggingInterpreter: Logger[IO] = Slf4jLogger.unsafeCreate[IO]

    private def toEvent(eventEnvelopes: EventEnvelope): Event =
      AvroSerializer.deserialise(eventEnvelopes.event)(Decoder[UserEvent], UserEvents.schema)

    private def eventToByteArray(evt: Event): Array[Byte] =
      AvroSerializer.serialise(evt.asInstanceOf[UserEvent])(Encoder[UserEvent], UserEvents.schema)

    private def insert1(identifier: String, sequence: Int, event: Event) = {

      loggingInterpreter.info(s"Persisting $identifier, $sequence")
      sql"""INSERT INTO events (identifier, sequence, event)
             VALUES ($identifier, $sequence, ${eventToByteArray(event)})
        """.update
        .withUniqueGeneratedKeys[Int]("id")
        .attemptSql
    }

    override def appendEventsTo[A](aggregateIdentifier: AggregateIdentifier[A],
                                   evt: List[Event],
                                   numberOfEvents: Int) = {

      evt
        .traverse(item => insert1(aggregateIdentifier.idAsString, numberOfEvents, item))
        .transact(Database.xa)
        .map(Traverse[List].sequence(_))
        .flatMap {
          case Left(exception) => IO.raiseError(exception)
          case Right(res) => IO(res.sum == evt.length)
        }

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
