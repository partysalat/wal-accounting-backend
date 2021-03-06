package org.justkile.wal.http.websocket

import cats.effect._
import fs2.Stream
import fs2.concurrent.Topic
import org.justkile.wal.projections.domain.FrontendNews

sealed trait Event
case class Text(value: String) extends Event
case object Quit extends Event
class NewsWebsocketQueue[F[_]: Sync: Effect: Concurrent](val topic: Topic[F, Option[FrontendNews]]) {
  def publish(news: FrontendNews) = topic.publish1(Some(news))

  def stream: Stream[F, Option[FrontendNews]] = topic.subscribe(1000)
}

object NewsWebsocketQueue {
  def apply[F[_]: NewsWebsocketQueue]: NewsWebsocketQueue[F] = implicitly
}
