package org.justkile.wal.user.http

import cats.effect._
import cats.implicits._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.HttpService
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl
import org.justkile.wal.user.algebra.UserRepository

class UserService[F[_] : Sync : UserRepository] extends Http4sDsl[F] {

  case class CreateUserRequest(name: String)

  val service: HttpService[F] = HttpService[F] {

    case req@GET -> Root => for {
      users <- UserRepository[F].getUsers
      result <- Ok(users)
    } yield result


    case req@POST -> Root => for {
      createUser <- req.as[CreateUserRequest]
      userOption <- UserRepository[F].addUser(createUser.name)
      result <- userOption match {
        case Some(user) => Created(user)
        case None => BadRequest("User already exists".asJson)
      }
    } yield result
  }
}
