package org.justkile.wal.event_sourcing

import cats.effect.Sync
import cats.syntax.flatMap._
import cats.syntax.functor._
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.utils.Done

trait Command[A] {
  def getAggregateIdentifier: AggregateIdentifier[A]
}

class CommandProcessor[F[_] : Sync : AggregateRepository:Logger] {

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
    } yield Done
  }

  private def persist[A: Aggregate]( events: List[Event], id: AggregateIdentifier[A], nbrOfExistingEvents: Int):F[Boolean] =
      AggregateRepository[F].persist(id, events, nbrOfExistingEvents)
}

object CommandProcessor {
  def apply[F[_]: CommandProcessor]: CommandProcessor[F] = implicitly
}