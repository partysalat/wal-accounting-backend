package org.justkile.wal.bootstrap

import cats.effect._
import cats.implicits._
import doobie.implicits._
import org.justkile.wal.core.achievements.AchievementDefinitions
import org.justkile.wal.core.drinks.domain.{Drink, DrinkType}
import org.justkile.wal.db.Database.xa
import org.justkile.wal.domain.UserCommands.CreateUserCommand
import org.justkile.wal.event_sourcing.CommandProcessor
import org.justkile.wal.utils.Done

class BootstrapService[F[_]: Sync: CommandProcessor] {
  private val users = List(
    CreateUserCommand("Albrecht", "1"),
    CreateUserCommand("Vivien", "2"),
    CreateUserCommand("Simon", "3"),
    CreateUserCommand("Benjamin Blöckere", "4"),
    CreateUserCommand("Stefan", "5"),
    CreateUserCommand("Paul", "6"),
    CreateUserCommand("Tori", "7"),
    CreateUserCommand("Benjamin Finger", "8"),
    CreateUserCommand("Phil", "9"),
    CreateUserCommand("Tina", "10"),
    CreateUserCommand("Resi", "11"),
    CreateUserCommand("Stephan", "12"),
    CreateUserCommand("Robert ", "13"),
    CreateUserCommand("Jenny", "14"),
    CreateUserCommand("Dana", "15"),
    CreateUserCommand("Jens", "16"),
    CreateUserCommand("Meike Rike Marion Maria Magdalena", "17"),
    CreateUserCommand("Thomas", "18"),
    CreateUserCommand("Julia", "19"),
    CreateUserCommand("Ben", "20"),
    CreateUserCommand("Cornelia", "21"),
    CreateUserCommand("Nasimausi", "22"),
    CreateUserCommand("Sophie", "23"),
    CreateUserCommand("Pierre Colin", "24"),
    CreateUserCommand("Noreen", "25"),
    CreateUserCommand("Florian", "26"),
    CreateUserCommand("Schröder", "27"),
    CreateUserCommand("Felix", "28"),
    CreateUserCommand("Lea", "29"),
    CreateUserCommand("Jan", "30"),
    CreateUserCommand("Catalina", "31"),
    CreateUserCommand("Saskia", "32"),
    CreateUserCommand("Charly", "33"),
    CreateUserCommand("Winnii", "34"),
    CreateUserCommand("Tinifini Zimmer", "35")
  )
  val sendInitialData: F[Done] = {
    for {
      _ <- users.map(CommandProcessor[F].process(_)).sequence
    } yield Done
  }

  import DrinkType._
  private val drinks = List(
    Drink(1, "Major Label", COCKTAIL),
    Drink(4, "KontinuumstransMojitor", COCKTAIL),
    Drink(5, "Dein Erzeuger", COCKTAIL),
    Drink(6, "Cassiopairinha", COCKTAIL),
    Drink(7, "Space Zombie", COCKTAIL),
    Drink(8, "Romulanisches Ale", COCKTAIL),
    Drink(9, "Deep Space 9 Iced Tea", COCKTAIL),
    Drink(10, "Mars Tai", COCKTAIL),
    Drink(11, "Nebulas Punch", COCKTAIL),
    Drink(12, "Galactic Sunrise", COCKTAIL),
    Drink(13, "Asgard Sling", COCKTAIL),
    Drink(14, "Solar Springtime Cooler", COCKTAIL),
    Drink(15, "Pineapple Star Fizz", COCKTAIL),
    Drink(16, "White Lady (Atom)", COCKTAIL),
    Drink(17, "Tom Collins böser Zwillingsbruder", COCKTAIL),
    Drink(18, "LeCosmoGurque", COCKTAIL),
    Drink(19, "Pluto Libre", COCKTAIL),
    Drink(20, "Victory Gin Tonic", COCKTAIL),
    Drink(21, "Klingonischer Blutwein", COCKTAIL),
    Drink(22, "Sonic Screwdriver", COCKTAIL),
    Drink(23, "Asgard Julep", COCKTAIL),
    Drink(25, "Mercury Mule", COCKTAIL),
    Drink(26, "White Glorb", COCKTAIL),
    Drink(27, "Comet Coitus", COCKTAIL),
    Drink(28, "Tschunk!", COCKTAIL),
    Drink(29, "Bloody Red Shirt", COCKTAIL),
    Drink(30, "Margarita 2000", COCKTAIL),
    Drink(31, "Berliner Luft", SHOT),
    Drink(32, "Wodka", SHOT),
    Drink(33, "Tequila", SHOT),
    Drink(34, "Polnische Rakede", SHOT),
    Drink(35, "Mexikaner", SHOT),
    Drink(35, "Whisky", SHOT),
    Drink(36, "Cola", SOFTDRINK),
    Drink(39, "Mate", SOFTDRINK),
    Drink(40, "Wasser", SOFTDRINK),
    Drink(41, "Orangensaft", SOFTDRINK),
    Drink(42, "Apfelsaft", SOFTDRINK),
    Drink(44, "Mogh's Wine", SOFTDRINK),
    Drink(45, "Mogh's Gimlet", SOFTDRINK),
    Drink(46, "Watermelon Margarita 2000", SOFTDRINK),
    Drink(47, "Virgin Comet Coitus", SOFTDRINK),
    Drink(48, "KontinuumstransMockitor", SOFTDRINK),
    Drink(49, "Virgin Mercury Mule", SOFTDRINK),
    Drink(50, "Shirleys Ginger Rocket", SOFTDRINK),
    Drink(50, "Hemingways Astro Sour", COCKTAIL),
    Drink(51, "Victory Gin Sour", COCKTAIL),
  ).map(drink => {
    sql"""MERGE INTO drinks
          KEY(id)
          VALUES  (${drink.id}, ${drink.name}, ${drink.`type`})
      """.update.run
  })
  private val achievements =
    List(AchievementDefinitions.eventBaseAchievements, AchievementDefinitions.spaceInvadersScoreAchievements).flatten
      .map(_.achievement)
      .map(achievement => {
        sql"""MERGE INTO achievements
          KEY(id)
          VALUES  (${achievement.id}, ${achievement.name}, ${achievement.description}, ${achievement.imagePath})
      """.update.run
      })

  val insertions: IO[Unit] = List(drinks, achievements).flatten
    .traverse_(_.transact(xa))

}
