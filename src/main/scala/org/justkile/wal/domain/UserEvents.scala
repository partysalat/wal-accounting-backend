package org.justkile.wal.domain

import com.sksamuel.avro4s.{AvroSchema, Decoder, Encoder}
import org.apache.avro.Schema
import org.justkile.wal.event_sourcing.Event

object UserEvents {
  sealed trait UserEvent extends Event
  case class UserCreated(id: String, name: String) extends UserEvent
  case class UserDrinkAdded(userId: String, drinkId: Int, amount: Int) extends UserEvent
  case class UserDrinkRemoved(userId: String, newsId: Int, drinkId: Int, amount: Int) extends UserEvent
  case class AchievementGained(userId: String, achievementId: Int) extends UserEvent
  case class AchievementRemoved(userId: String, achievementId: Int) extends UserEvent
  case class ScoreSet(userId: String, score: Long) extends UserEvent

//  case class RfidCardSet(userId: String, rfid: String) extends UserEvent
//  case class KegCreated(userId: String, kegId: String) extends UserEvent // <- Different aggregate / plain CRUD

//  case class DraftStarted(userId: String, kegId: String) extends UserEvent
//  case class DraftEnded(userId: String, kegId: String) extends UserEvent
//  case class DraftAmountUpdated(userId: String, kegId: String) extends UserEvent

  implicit val schema: Schema = AvroSchema[UserEvent]
}
