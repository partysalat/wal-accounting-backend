package org.justkile.wal.event_sourcing

import cats.Traverse
import cats.effect.Sync
import cats.syntax.flatMap._
import cats.syntax.functor._
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.event_sourcing.event_bus.EventBus
import org.justkile.wal.utils.Done

trait Command[A] {
  def getAggregateIdentifier: AggregateIdentifier[A]
}

class CommandProcessor[F[_]: Sync: AggregateRepository: Logger: EventBus] {

  def process[A: Aggregate](command: Command[A]): F[Done] = {
    for {
      _ <- Logger[F].info(s"Processing command $command ")
      aggregateNumberOfEvtTuple <- AggregateRepository[F].load[A](command.getAggregateIdentifier)
      (aggregate, nbrOfExistingEvents) = aggregateNumberOfEvtTuple
      transformedAggregateEventsTuple = Aggregate[A]
        .processCommand(command)
        .run(aggregate)
        .value
      (_, events) = transformedAggregateEventsTuple
      _ <- persist(events, command.getAggregateIdentifier, nbrOfExistingEvents)
      _ <- publish(events)
    } yield Done
  }

  private def persist[A: Aggregate](events: List[Event],
                                    id: AggregateIdentifier[A],
                                    nbrOfExistingEvents: Int): F[Boolean] =
    AggregateRepository[F].persist(id, events, nbrOfExistingEvents)

  private def publish(events: List[Event]) = {
    import cats.implicits._
    for {
      _ <- Logger[F].info(s"Publish events $events ")
      publishings = events.map(EventBus[F].publish[Event](_))
      res <- Traverse[List].sequence(publishings)
    } yield res

  }
}

object CommandProcessor {
  def apply[F[_]: CommandProcessor]: CommandProcessor[F] = implicitly
}
