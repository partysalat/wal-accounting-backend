package org.justkile.wal.user.http.websocket

import cats.effect._
import fs2.Stream
import fs2.concurrent.Topic
import org.justkile.wal.user.domain.JoinedNews

sealed trait Event
case class Text(value: String) extends Event
case object Quit extends Event
class NewsWebsocketQueue[F[_]: Sync: Effect: Concurrent](val topic: Topic[F, Option[JoinedNews]]) {
//  val topicF: F[Topic[F, Option[JoinedNews]]] = Topic[F, Option[JoinedNews]](None)
  def publish(news: JoinedNews) = topic.publish1(Some(news))

  def stream: Stream[F, Option[JoinedNews]] = topic.subscribe(1000)
}

object NewsWebsocketQueue {
  def apply[F[_]: NewsWebsocketQueue]: NewsWebsocketQueue[F] = implicitly
}
