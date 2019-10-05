package org.justkile.wal

import java.util.concurrent.ForkJoinPool

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import fs2.Stream
import fs2.concurrent.Topic
import org.http4s.server.blaze.BlazeBuilder
import org.justkile.wal.db.Database
import org.justkile.wal.core.drinks.DrinkRepositoryIO._
import org.justkile.wal.event_sourcing.CommandProcessorIO._
import org.justkile.wal.event_sourcing.event_bus.EventBusIO._
import org.justkile.wal.bootstrap.BootstrapService
import org.justkile.wal.event_handlers.UserEventHandlers
import org.justkile.wal.http.websocket.NewsWebsocketQueue
import org.justkile.wal.http.{DrinkService, NewsService, UserService}
import org.justkile.wal.projections.domain.FrontendNews
import org.justkile.wal.projections.AchievementRepositoryIO._
import org.justkile.wal.projections.BestlistRepositoryIO._
import org.justkile.wal.projections.NewsRepositoryIO._
import org.justkile.wal.projections.UserRepositoryIO._
import org.justkile.wal.utils.LoggerIO._

import scala.concurrent.ExecutionContext

object Server extends IOApp {

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(new ForkJoinPool(16))

  def stream(args: List[String]): Stream[IO, Unit] =
    for {
      topic <- Stream.eval(Topic[IO, Option[FrontendNews]](None))
      websocketQueue = new NewsWebsocketQueue[IO](topic)
      _ <- Stream.eval(Database.schemaDefinition)
      _ <- Stream.eval(new BootstrapService[IO].insertions)
      _ <- Stream.eval(new UserEventHandlers[IO](websocketQueue).start)
      _ <- Stream.eval(new BootstrapService[IO].sendInitialData)

      _ <- BlazeBuilder[IO]
        .bindHttp()
        .mountService(new UserService[IO].service, "/api/users")
        .mountService(new NewsService[IO](websocketQueue).service, "/api/news")
        .mountService(new DrinkService[IO].service, "/api/drinks")
        .withExecutionContext(ec)
        .serve
    } yield ()

  def run(args: List[String]): IO[ExitCode] =
    stream(args).compile.drain.as(ExitCode.Success)
}
