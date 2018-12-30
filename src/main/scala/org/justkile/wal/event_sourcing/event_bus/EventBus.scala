package org.justkile.wal.event_sourcing.event_bus

import cats.Traverse
import cats.effect.Sync
import cats.implicits._
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.utils.Done

import scala.reflect.ClassTag

class EventBus[F[_]: Sync: Logger] {

  import org.justkile.wal.event_sourcing.event_bus.EventBus.EventHandler

  lazy val eventHandlerRegistry = new Registry[F]

  def subscribe[T](e: EventHandler[F, T])(implicit c: ClassTag[T]): F[Done] = {
    for {
      _ <- Logger[F].info("Subscribe event handler")
      _ = eventHandlerRegistry.registerEventHandler[T](e)
    } yield Done
  }

  def publish[E](event: E): F[Done] = {
    for {
      _ <- Logger[F].info(s"Publish event $event")
      executedEventHandlers = eventHandlerRegistry.lookUpEventHandler(event).map(_.handle(event))
      _ <- Traverse[List].sequence(executedEventHandlers)
    } yield Done
  }
}

object EventBus {

  trait EventHandler[F[_], E] {
    def handle(event: E): F[Done]
  }

  def apply[F[_]: EventBus]: EventBus[F] = implicitly
}
