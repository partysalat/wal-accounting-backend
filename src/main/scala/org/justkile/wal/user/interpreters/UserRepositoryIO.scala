package org.justkile.wal.user.interpreters

import cats.effect.IO
import doobie.implicits._
import org.justkile.wal.db.Database
import org.justkile.wal.user.algebras.UserRepository
import org.justkile.wal.user.domain.UserProjection

object UserRepositoryIO {
  implicit def userRepoInterpreter: UserRepository[IO] = new UserRepository[IO] {
    def addUser(userId: String, name: String): IO[Option[UserProjection]] =
      sql"INSERT INTO users (userId, name) VALUES ($userId, $name)".update
        .withUniqueGeneratedKeys[Int]("id")
        .attemptSql
        .map(_.toOption.map(id => UserProjection(id, userId, name)))
        .transact(Database.xa)

    def getUsers: IO[List[UserProjection]] =
      sql"""
        SELECT id, userId, name
        FROM users
      """
        .query[UserProjection]
        .to[List]
        .transact(Database.xa)
  }
}
