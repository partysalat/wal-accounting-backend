package org.justkile.wal.user.bootstrap

import cats.effect._
import cats.implicits._
import org.justkile.wal.event_sourcing.CommandProcessor
import org.justkile.wal.user.domain.User.CreateUserCommand
import org.justkile.wal.utils.Done

class BootstrapService[F[_]: Sync: CommandProcessor] {
  private val users = List(
    CreateUserCommand("Albrecht", "1"),
    CreateUserCommand("Vivien", "2"),
    CreateUserCommand("Simon", "3"),
    CreateUserCommand("Benjamin Blöckere", "4"),
    CreateUserCommand("Stefan", "5"),
    CreateUserCommand("Paul", "6"),
    CreateUserCommand("Tori", "7"),
    CreateUserCommand("Benjamin Finger", "8"),
    CreateUserCommand("Phil", "9"),
    CreateUserCommand("Tina", "10"),
    CreateUserCommand("Resi", "11"),
    CreateUserCommand("Stephan", "12"),
    CreateUserCommand("Robert ", "13"),
    CreateUserCommand("Jenny", "14"),
    CreateUserCommand("Dana", "15"),
    CreateUserCommand("Jens", "16"),
    CreateUserCommand("Meike Rike Marion Maria Magdalena", "17"),
    CreateUserCommand("Thomas", "18"),
    CreateUserCommand("Julia", "19"),
    CreateUserCommand("Ben", "20"),
    CreateUserCommand("Cornelia", "21"),
    CreateUserCommand("Nasimausi", "22"),
    CreateUserCommand("Sophie", "23"),
    CreateUserCommand("Pierre Colin", "24"),
    CreateUserCommand("Noreen", "25"),
    CreateUserCommand("Florian", "26"),
    CreateUserCommand("Christian", "27"),
    CreateUserCommand("Felix", "28"),
    CreateUserCommand("Lea", "29"),
    CreateUserCommand("Jan", "30"),
    CreateUserCommand("Catalina", "31"),
    CreateUserCommand("Saskia", "32")
  )
  val sendInitialData: F[Done] = {
    for {
      _ <- users.map(CommandProcessor[F].process(_)).sequence
    } yield Done
  }
}
