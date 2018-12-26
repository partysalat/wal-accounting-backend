package org.justkile.wal.utils

import cats.effect.IO
import io.chrisdavenport.log4cats.Logger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger

object LoggerIO {
  implicit def loggingInterpreter:Logger[IO] = Slf4jLogger.unsafeCreate[IO]
}
