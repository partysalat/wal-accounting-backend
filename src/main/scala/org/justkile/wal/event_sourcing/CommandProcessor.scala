package org.justkile.wal.event_sourcing

import cats.effect.Sync
import cats.syntax.flatMap._
import cats.syntax.functor._
import org.justkile.wal.utils.Done

trait Command[A] {
  def getAggregateIdentifier: AggregateIdentifier[A]
}

class CommandProcessor[F[_] : Sync : AggregateRepository, A: Aggregate] {

  def process(command: Command[A]): F[Done] = {
    for {
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

  private def persist( events: List[Event], id: AggregateIdentifier[A], nbrOfExistingEvents: Int):F[Boolean] =
      AggregateRepository[F].persist(id, events, nbrOfExistingEvents)
}

//object CommandProcessor {
//  def apply[F[_]: CommandProcessor, A]: CommandProcessor[F] = implicitly
//}