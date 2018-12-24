package org.justkile.wal.user.interpreters

import cats.effect.IO
import org.justkile.wal.db.Database
import org.justkile.wal.user.algebra.UserRepository
import org.justkile.wal.user.model.User
import doobie.implicits._

object UserRepositoryIO {
  implicit def userRepoInterpreter: UserRepository[IO] = new UserRepository[IO] {
    def addUser(username: String): IO[Option[User]] =
      sql"INSERT INTO users (name) VALUES ($username)"
        .update
        .withUniqueGeneratedKeys[Int]("id")
        .attemptSql
        .map(_.toOption.map(id => User(id, username)))
        .transact(Database.xa)

    def getUsers: IO[List[User]] = sql"""
        SELECT id, name
        FROM users
      """
      .query[User]
      .to[List]
      .transact(Database.xa)
  }
}
