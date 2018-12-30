package org.justkile.wal.user.http

import cats.effect._
import cats.implicits._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.HttpService
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl
import org.justkile.wal.event_sourcing.{AggregateRepository, Command, CommandProcessor}
import org.justkile.wal.user.algebra.UserRepository
import org.justkile.wal.user.model.{CreateUserCommand, User, UserIdentifier}

class UserService[F[_] : Sync : UserRepository : CommandProcessor : AggregateRepository] extends Http4sDsl[F] {

  case class CreateUserRequest(name: String)
  val service: HttpService[F] = HttpService[F] {

    case req@GET -> Root / userId => for {
      userTuple <- AggregateRepository[F].load(UserIdentifier(userId, ""))
      (user, _) = userTuple
      result <- Ok(user)
    } yield result


    case req@POST -> Root => for {
      createUser <- req.as[CreateUserRequest]
      createUserCommand = CreateUserCommand(createUser.name)
      _ <- CommandProcessor[F].process[User](createUserCommand)
      result <- Created()
    } yield result
  }
}