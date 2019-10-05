package org.justkile.wal.http

import cats.effect._
import cats.implicits._
import io.chrisdavenport.log4cats.Logger
import io.circe.generic.auto._
import io.circe.syntax._
import fs2._
import org.http4s.HttpRoutes
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.dsl.impl.QueryParamDecoderMatcher
import org.http4s.server.websocket.WebSocketBuilder
import org.http4s.websocket.WebSocketFrame
import org.http4s.websocket.WebSocketFrame.Text
import org.justkile.wal.domain.User
import org.justkile.wal.domain.UserCommands.RemoveUserDrinkCommand
import org.justkile.wal.event_sourcing.CommandProcessor
import org.justkile.wal.http.websocket.NewsWebsocketQueue
import org.justkile.wal.projections.domain.DrinkPayload
import org.justkile.wal.projections.NewsRepository

object Offset extends QueryParamDecoderMatcher[Int]("offset")
object Filter extends QueryParamDecoderMatcher[String]("filter")

class NewsService[F[_]: Sync: NewsRepository: CommandProcessor: Logger: Timer](websocketQueue: NewsWebsocketQueue[F])(
    implicit F: Effect[F])
    extends Http4sDsl[F] {
  val PAGE_SIZE = 20
  val service: HttpRoutes[F] = HttpRoutes.of[F] {

    case req @ GET -> Root :? Offset(skip) +& Filter("drinks") =>
      for {
        news <- NewsRepository[F].getDrinkNews(skip, PAGE_SIZE)
        res <- Ok(news.asJson)
      } yield res

    case req @ GET -> Root :? Offset(skip) =>
      for {
        news <- NewsRepository[F].getNews(skip, PAGE_SIZE)

        res <- Ok(news.asJson)
      } yield res

    case req @ DELETE -> Root / IntVar(newsId) =>
      for {
        newsItem <- NewsRepository[F].getNewsItem(newsId)

        drinkOpt = newsItem.payload match {
          case drink: DrinkPayload => Some(drink)
          case _ => None
        }
        commandOpt = drinkOpt.map(drink =>
          RemoveUserDrinkCommand(newsItem.user.userId, newsId, drink.id, newsItem.news.amount))
        res <- commandOpt match {
          case Some(command) => CommandProcessor[F].process[User](command).flatMap(_ => NoContent())
          case None => BadRequest()
        }
      } yield res

    case req @ GET -> Root / "csv" =>
      for {
        news <- NewsRepository[F].getDrinkNews(0, Integer.MAX_VALUE)
        csv = news
          .map(newsItem => {
            newsItem.payload match {
              case DrinkPayload(id, drinkName, drinkType) =>
                List(newsItem.user.name, drinkType, drinkName, newsItem.news.amount, newsItem.news.createdAt)
                  .mkString(",")
              case _ => ""
            }
          })
          .mkString("\n")

        res <- Ok(csv)
      } yield res

    case GET -> Root / "ws" =>
      val toClient: Stream[F, WebSocketFrame] =
        websocketQueue.stream.map(news => Text(news.asJson.toString))
      val fromClient: Pipe[F, WebSocketFrame, Unit] = _.evalMap {
        case Text(t, _) => F.delay(println(t))
        case f => F.delay(println(s"Unknown type: $f"))
      }
      WebSocketBuilder[F].build(toClient, fromClient)

  }
}
