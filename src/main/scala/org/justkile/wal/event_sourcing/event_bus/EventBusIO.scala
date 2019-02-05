package org.justkile.wal.event_sourcing.event_bus

import cats.effect.IO
import org.justkile.wal.utils.LoggerIO._

object EventBusIO {

  implicit val eventBusIO: EventBus[IO] = new EventBus[IO]
}
