package org.justkile.wal.user.algebras

import org.justkile.wal.user.domain.UserProjection

trait UserRepository[F[_]] {
  def addUser(userId: String, name: String): F[Option[UserProjection]]
  def getUsers: F[List[UserProjection]]
}

object UserRepository {
  def apply[F[_]: UserRepository]: UserRepository[F] = implicitly
}
