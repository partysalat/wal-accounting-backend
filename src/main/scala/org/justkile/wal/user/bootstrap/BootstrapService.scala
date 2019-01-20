package org.justkile.wal.user.bootstrap

import cats.effect._
import cats.implicits._
import org.justkile.wal.event_sourcing.CommandProcessor
import org.justkile.wal.user.domain.User.CreateUserCommand
import org.justkile.wal.utils.Done

class BootstrapService[F[_]: Sync: CommandProcessor] {
  private val users = List(
    CreateUserCommand("Ben", "00001"),
    CreateUserCommand("Felix", "00002"),
    CreateUserCommand("Jenny", "00003"),
    CreateUserCommand("Thomas", "00004"),
    CreateUserCommand("Meike", "00005"),
    CreateUserCommand("Benni B. ", "00006"),
    CreateUserCommand("Flo", "00007"),
    CreateUserCommand("Paul", "00008"),
    CreateUserCommand("Tori", "00009"),
    CreateUserCommand("Phil", "000010")
  )
  val sendInitialData: F[Done] = {
    for {
      _ <- users.map(CommandProcessor[F].process(_)).sequence
    } yield Done
  }
}
