package org.justkile.wal.event_sourcing.event_bus

import org.justkile.wal.event_sourcing.event_bus.EventBus.EventHandler
import org.justkile.wal.utils.Done

import scala.reflect.ClassTag

trait EventBus[F[_]] {

  def subscribe[T: ClassTag](e: EventHandler[F, T]): F[Done]
  def publish[E](event: E): F[Done]
}

object EventBus {

  trait EventHandler[F[_], E] {
    def handle(event: E): F[Done]
  }

  def apply[F[_]: EventBus]: EventBus[F] = implicitly
}
