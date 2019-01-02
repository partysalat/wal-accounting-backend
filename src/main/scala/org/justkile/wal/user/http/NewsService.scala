package org.justkile.wal.user.http

import cats.effect._
import cats.implicits._
import io.circe.generic.auto._
import org.http4s.HttpService
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl
import org.justkile.wal.user.algebras.NewsRepository

class NewsService[F[_]: Sync: NewsRepository] extends Http4sDsl[F] {
  val PAGE_SIZE = 20
  val service: HttpService[F] = HttpService[F] {

    case req @ GET -> Root / IntVar(skip) =>
      for {
        news <- NewsRepository[F].getNews(skip, PAGE_SIZE)
        result <- Ok(news)
      } yield result

  }
}
