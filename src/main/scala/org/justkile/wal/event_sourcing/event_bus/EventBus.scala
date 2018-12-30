package org.justkile.wal.event_sourcing.event_bus

import cats.Applicative
import cats.effect.Sync
import cats.implicits._
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.utils.Done

import scala.reflect.ClassTag

class EventBus[F[_]: Sync: Logger: Applicative] {

  import org.justkile.wal.event_sourcing.event_bus.EventBus.EventHandler

  lazy val eventHandlerRegistry = new Registry[F]

  def subscribe[T](e: EventHandler[F, T])(implicit c: ClassTag[T], F: Applicative[F]): F[Done] = {
    for {
      _ <- F.pure(eventHandlerRegistry.registerEventHandler[T](e))
    } yield Done
  }

  def publish[E](event: E)(implicit F: Applicative[F]): F[Done] = {
    for {
      _ <- Logger[F].info(s"Publish event $event")
      executedEventHandlers <- F.pure(eventHandlerRegistry.lookUpEventHandler(event).map(_.handle(event)))
      _ <- executedEventHandlers.sequence
    } yield Done
  }
}

object EventBus {

  trait EventHandler[F[_], E] {
    def handle(event: E): F[Done]
  }

  def apply[F[_]: EventBus]: EventBus[F] = implicitly
}
