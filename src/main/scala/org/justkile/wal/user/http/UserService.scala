package org.justkile.wal.user.http

import cats.Applicative
import cats.effect._
import cats.implicits._
import io.circe.generic.auto._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl
import org.justkile.wal.drinks.domain.DrinkType
import org.justkile.wal.event_sourcing.{AggregateRepository, CommandProcessor}
import org.justkile.wal.user.algebras.{AchievementRepository, BestlistRepository, UserRepository}
import org.justkile.wal.user.domain.User
import org.justkile.wal.user.domain.User._
import org.justkile.wal.user.interpreters.AchievementRepositoryIO

class UserService[F[_]: Sync: AchievementRepository: CommandProcessor: UserRepository: BestlistRepository: Applicative]
    extends Http4sDsl[F] {

  case class CreateUserRequest(name: String)
  case class UserNews(userId: String, amount: Int)
  case class AddDrinkRequest(drinkId: Int, users: List[UserNews])

  val service: HttpService[F] = HttpService[F] {

    case req @ GET -> Root =>
      for {
        users <- UserRepository[F].getUsers
        result <- Ok(users)
      } yield result

    case req @ GET -> Root / userId / "ach" =>
      for {
        achievementUserStats <- AchievementRepository[F].getDrinkEventsForUser(userId)
        result <- Ok(achievementUserStats)
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

    case req @ DELETE -> Root / userId / "news" / IntVar(newsId) =>
      for {
        command <- Applicative[F].pure(RemoveUserDrinkCommand(userId, newsId))
        _ <- CommandProcessor[F].process[User](command)
        result <- NoContent()
      } yield result

    case req @ GET -> Root / "bestlist" =>
      for {
        res <- BestlistRepository[F].getStats()
        sortedResult = res.sortBy(item => -(item.beerCount + item.cocktailCount))
        result <- Ok(sortedResult)
      } yield result
  }
}
