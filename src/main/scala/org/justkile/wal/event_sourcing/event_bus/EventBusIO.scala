package org.justkile.wal.event_sourcing.event_bus

import cats.effect.IO
import io.chrisdavenport.log4cats.Logger
import org.justkile.wal.utils.Done
import org.justkile.wal.utils.LoggerIO._
import cats.implicits._

import scala.reflect.ClassTag

object EventBusIO {

  implicit val eventBusIO: EventBus[IO] = new EventBus[IO] {

    lazy val eventHandlerRegistry = new Registry[IO]

    override def subscribe[T: ClassTag](e: EventBus.EventHandler[IO, T]): IO[Done] =
      for {
        _ <- IO.pure(eventHandlerRegistry.registerEventHandler[T](e))
      } yield Done

    override def publish[E](event: E): IO[Done] =
      for {
        _ <- Logger[IO].info(s"Publish event $event")
        executedEventHandlers <- IO.pure(eventHandlerRegistry.lookUpEventHandler(event).map(_.handle(event)))
        _ = executedEventHandlers.sequence.unsafeRunAsyncAndForget()
      } yield Done

  }
}
