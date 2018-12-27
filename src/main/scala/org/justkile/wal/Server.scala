package org.justkile.wal

import java.util.concurrent.ForkJoinPool

import cats.effect.IO
import cats.implicits._
import fs2.StreamApp.ExitCode
import fs2.{Stream, StreamApp}
import org.http4s.server.blaze.BlazeBuilder
import org.justkile.wal.db.Database
import org.justkile.wal.user.http.UserService
import org.justkile.wal.user.interpreters.UserRepositoryIO._

import scala.concurrent.ExecutionContext

object Server extends StreamApp[IO] {

  implicit val ec:ExecutionContext = ExecutionContext.fromExecutor(new ForkJoinPool(20))
  override def stream(args: List[String], requestShutdown: IO[Unit]): Stream[IO, ExitCode] =
    Stream.eval(Database.schemaDefinition) *>
      BlazeBuilder[IO]
        .bindHttp()
        .mountService(new UserService[IO].service, "/api/user")
        .withExecutionContext(ec)

        .serve

}
