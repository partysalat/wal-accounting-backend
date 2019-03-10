package org.justkile.wal

import java.util.concurrent.ForkJoinPool
import cats.effect._
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.syntax.kleisli._
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import fs2.Stream
import org.http4s.server.blaze.BlazeBuilder
import org.justkile.wal.db.Database
import org.justkile.wal.drinks.http.DrinkService
import org.justkile.wal.drinks.interpreters.DrinkRepositoryIO._
import org.justkile.wal.event_sourcing.CommandProcessorIO._
import org.justkile.wal.event_sourcing.event_bus.EventBusIO._
import org.justkile.wal.user.bootstrap.BootstrapService
import org.justkile.wal.user.events.UserEvents
import org.justkile.wal.user.http.{NewsService, UserService}
import org.justkile.wal.user.interpreters.AchievementRepositoryIO._
import org.justkile.wal.user.interpreters.BestlistRepositoryIO._
import org.justkile.wal.user.interpreters.NewsRepositoryIO._
import org.justkile.wal.user.interpreters.UserRepositoryIO._
import org.justkile.wal.utils.LoggerIO._

import scala.concurrent.ExecutionContext

object Server extends IOApp {

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(new ForkJoinPool(16))

  def stream(args: List[String]): Stream[IO, ExitCode] =
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

  def run(args: List[String]): IO[ExitCode] =
    stream(args).compile.drain.as(ExitCode.Success)
}
