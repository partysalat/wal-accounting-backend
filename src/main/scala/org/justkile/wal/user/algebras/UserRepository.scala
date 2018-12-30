package org.justkile.wal.user.algebras

import org.justkile.wal.user.domain.User

trait UserRepository[F[_]] {
  def addUser(username: String, email: String): F[Option[User]]
}

object UserRepository {
  def apply[F[_]: UserRepository]: UserRepository[F] = implicitly
}
