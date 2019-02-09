package org.justkile.wal

import java.util.concurrent.ForkJoinPool

import cats.effect.IO
import cats.implicits._
import fs2.StreamApp.ExitCode
import fs2.{Stream, StreamApp}
import org.http4s.server.blaze.BlazeBuilder
import org.justkile.wal.db.Database
import org.justkile.wal.user.bootstrap.BootstrapService
import org.justkile.wal.drinks.http.DrinkService
import org.justkile.wal.user.http.{NewsService, UserService}
import org.justkile.wal.event_sourcing.CommandProcessorIO._
import org.justkile.wal.event_sourcing.event_bus.EventBusIO._
import org.justkile.wal.user.events.UserEvents
import org.justkile.wal.utils.LoggerIO._
import org.justkile.wal.user.interpreters.UserRepositoryIO._
import org.justkile.wal.drinks.interpreters.DrinkRepositoryIO._
import org.justkile.wal.user.interpreters.NewsRepositoryIO._
import org.justkile.wal.user.interpreters.AchievementRepositoryIO._
import org.justkile.wal.user.interpreters.BestlistRepositoryIO._

import scala.concurrent.ExecutionContext

object Server extends StreamApp[IO] {

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(new ForkJoinPool(16))

  override def stream(args: List[String], requestShutdown: IO[Unit]): Stream[IO, ExitCode] =
    Stream.eval(Database.schemaDefinition) *>
      Stream.eval(Database.insertions) *>
      Stream.eval(new UserEvents[IO].start) *>
      Stream.eval(new BootstrapService[IO].sendInitialData) *>
      BlazeBuilder[IO]
        .bindHttp()
        .mountService(new UserService[IO].service, "/api/users")
        .mountService(new DrinkService[IO].service, "/api/drinks")
        .mountService(new NewsService[IO].service, "/api/news")
        .withExecutionContext(ec)
        .serve

}
