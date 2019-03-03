package org.justkile.wal.user.http

import cats.effect._
import cats.implicits._
import io.chrisdavenport.log4cats.Logger
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl
import org.http4s.circe._
import org.justkile.wal.event_sourcing.CommandProcessor
import org.justkile.wal.user.algebras.NewsRepository
import org.justkile.wal.user.domain.{DrinkPayload, User}
import org.justkile.wal.user.domain.User.RemoveUserDrinkCommand

class NewsService[F[_]: Sync: NewsRepository: CommandProcessor: Logger] extends Http4sDsl[F] {
  val PAGE_SIZE = 20
  val service: HttpService[F] = HttpService[F] {

    case req @ GET -> Root / IntVar(skip) =>
      for {
        news <- NewsRepository[F].getNews(skip, PAGE_SIZE)
        res <- Ok(news.asJson)
      } yield res
    case req @ GET -> Root / "csv" =>
      for {
        news <- NewsRepository[F].getDrinkNews(0, Integer.MAX_VALUE)
        csv = news
          .map(newsItem => {
            newsItem.payload match {
              case DrinkPayload(id, drinkName, drinkType) =>
                List(newsItem.user.name, drinkName, newsItem.news.amount).mkString(",")
              case _ => ""
            }
          })
          .mkString("\n")

        res <- Ok(csv)
      } yield res

    case req @ GET -> Root / IntVar(skip) / drinks =>
      for {
        news <- NewsRepository[F].getDrinkNews(skip, PAGE_SIZE)
        res <- Ok(news.asJson)
      } yield res

    case req @ DELETE -> Root / "item" / IntVar(newsId) =>
      for {
        newsItem <- NewsRepository[F].getNewsItem(newsId)

        drinkOpt = newsItem.payload match {
          case drink: DrinkPayload => Some(drink)
          case _ => None
        }
        commandOpt: Option[RemoveUserDrinkCommand] = drinkOpt.map(drink =>
          RemoveUserDrinkCommand(newsItem.user.userId, newsId, drink.id, newsItem.news.amount))
        res <- commandOpt match {
          case Some(command) => CommandProcessor[F].process[User](command).flatMap(_ => NoContent())
          case None => BadRequest()
        }
      } yield res

  }
}
