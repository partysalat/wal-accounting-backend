package org.justkile.wal.domain

import java.util.UUID.randomUUID

import org.justkile.wal.domain.User.UserIdentifier
import org.justkile.wal.event_sourcing.{AggregateIdentifier, Command}

object UserCommands {
  case class CreateUserCommand(name: String, id: String = randomUUID().toString) extends Command[User] {
    override def getAggregateIdentifier: AggregateIdentifier[User] = UserIdentifier(id)
  }

  case class AddUserDrinkCommand(userId: String, drinkId: Int, amount: Int) extends Command[User] {
    override def getAggregateIdentifier: AggregateIdentifier[User] = UserIdentifier(userId)
  }
  case class SetUserScoreCommand(userId: String, score: Long) extends Command[User] {
    override def getAggregateIdentifier: AggregateIdentifier[User] = UserIdentifier(userId)
  }
  case class RemoveUserDrinkCommand(userId: String, newsId: Int, drinkId: Int, amount: Int) extends Command[User] {
    override def getAggregateIdentifier: AggregateIdentifier[User] = UserIdentifier(userId)
  }
  case class GainAchievement(userId: String, achievementId: Int) extends Command[User] {
    override def getAggregateIdentifier: AggregateIdentifier[User] = UserIdentifier(userId)
  }
  case class RemoveAchievement(userId: String, achievementId: Int) extends Command[User] {
    override def getAggregateIdentifier: AggregateIdentifier[User] = UserIdentifier(userId)
  }

}
