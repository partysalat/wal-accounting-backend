package org.justkile.wal.user.algebra

import org.justkile.wal.user.model.User

trait UserRepository[F[_]] {
  def addUser(username: String): F[Option[User]]
  def getUsers: F[List[User]]
}

object UserRepository {
  def apply[F[_]: UserRepository]: UserRepository[F] = implicitly
}