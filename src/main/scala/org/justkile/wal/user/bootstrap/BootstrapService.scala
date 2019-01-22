package org.justkile.wal.user.bootstrap

import cats.effect._
import cats.implicits._
import org.justkile.wal.event_sourcing.CommandProcessor
import org.justkile.wal.user.domain.User.CreateUserCommand
import org.justkile.wal.utils.Done

class BootstrapService[F[_]: Sync: CommandProcessor] {
  private val users = List(
    CreateUserCommand("Ben", "1"),
    CreateUserCommand("Thomas", "2"),
    CreateUserCommand("Winnii", "3"),
    CreateUserCommand("Flo", "4"),
    CreateUserCommand("Sophie", "5"),
    CreateUserCommand("Jan", "6"),
    CreateUserCommand("Benni F.", "7"),
    CreateUserCommand("Andrej", "8"),
    CreateUserCommand("Conni Lohann", "9"),
    CreateUserCommand("Nasimausi", "10"),
    CreateUserCommand("Tina", "11"),
    CreateUserCommand("Simon", "12"),
    CreateUserCommand("Noreen", "13"),
    CreateUserCommand("Vivien", "14"),
    CreateUserCommand("Benni B.", "15"),
    CreateUserCommand("Paul Boeck", "16"),
    CreateUserCommand("Meike", "17"),
    CreateUserCommand("Felix", "18"),
    CreateUserCommand("Tori", "19"),
    CreateUserCommand("Jenny", "20"),
    CreateUserCommand("Stephan Grunwald", "21"),
    CreateUserCommand("Phil", "22"),
    CreateUserCommand("Caro", "23"),
    CreateUserCommand("Resi", "24"),
    CreateUserCommand("Saskia", "25"),
    CreateUserCommand("Robert", "26"),
    CreateUserCommand("Franzi", "27"),
    CreateUserCommand("Ludwig", "28"),
    CreateUserCommand("Jens", "29"),
    CreateUserCommand("Dana", "30"),
    CreateUserCommand("Rike", "31"),
    CreateUserCommand("Julia", "32")
  )
  val sendInitialData: F[Done] = {
    for {
      _ <- users.map(CommandProcessor[F].process(_)).sequence
    } yield Done
  }
}
