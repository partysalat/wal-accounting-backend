package org.justkile.wal.http

import cats.Applicative
import cats.effect._
import cats.implicits._
import io.circe.generic.auto._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl
import org.justkile.wal.domain.User
import org.justkile.wal.domain.User._
import org.justkile.wal.domain.UserCommands.{AddUserDrinkCommand, CreateUserCommand, SetUserScoreCommand}
import org.justkile.wal.event_sourcing.CommandProcessor
import org.justkile.wal.projections.{AchievementRepository, BestlistRepository, UserRepository}

class UserService[F[_]: Sync: AchievementRepository: CommandProcessor: UserRepository: BestlistRepository: Applicative]
    extends Http4sDsl[F] {

  case class CreateUserRequest(name: String)
  case class UserNews(userId: String, amount: Int)
  case class AddDrinkRequest(drinkId: Int, users: List[UserNews])
  case class AddScoreRequest(userId: String, score: Long)

  val service: HttpRoutes[F] = HttpRoutes.of[F] {

    case req @ GET -> Root =>
      for {
        users <- UserRepository[F].getUsers
        result <- Ok(users)
      } yield result

    case req @ POST -> Root =>
      for {
        createUser <- req.as[CreateUserRequest]
        createUserCommand = CreateUserCommand(createUser.name)
        _ <- CommandProcessor[F].process[User](createUserCommand)
        result <- Created()
      } yield result

    case req @ POST -> Root / "drink" =>
      for {
        addDrinkRequest <- req.as[AddDrinkRequest]
        addDrinkCommands = addDrinkRequest.users.map(userNews =>
          AddUserDrinkCommand(userNews.userId, addDrinkRequest.drinkId, userNews.amount))
        res <- addDrinkCommands.map(CommandProcessor[F].process[User](_)).sequence
        result <- Created(res)
      } yield result

    case req @ POST -> Root / "space-invaders" =>
      for {
        setScoreRequest <- req.as[AddScoreRequest]
        setScoreCommand = SetUserScoreCommand(setScoreRequest.userId, setScoreRequest.score)
        res <- CommandProcessor[F].process[User](setScoreCommand)
        result <- Created(res)
      } yield result

//TODO: use gzip
    case req @ GET -> Root / "bestlist" =>
      for {
        res <- BestlistRepository[F].getStats()
        sortedResult = res.sortBy(item => -(item.beerCount + item.cocktailCount))
        result <- Ok(sortedResult)
      } yield result
  }
}
