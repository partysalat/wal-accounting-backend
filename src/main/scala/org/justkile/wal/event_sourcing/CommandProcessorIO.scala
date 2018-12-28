package org.justkile.wal.event_sourcing

import cats.effect.IO
import org.justkile.wal.event_sourcing.store.EventStoreIO._
import org.justkile.wal.utils.LoggerIO._

object CommandProcessorIO {

  implicit def aggregateRepository: AggregateRepository[IO] = new AggregateRepository[IO]
  implicit def commandProcessorIO: CommandProcessor[IO] = new CommandProcessor[IO]
}
