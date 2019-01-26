package org.justkile.wal.user.events.achievements

import org.justkile.wal.drinks.domain.DrinkType
import org.justkile.wal.drinks.domain.DrinkType.DrinkType
import org.justkile.wal.user.domain.UserDrinkEvent

case class Achievement(
    id: Int,
    name: String,
    description: String,
    imagePath: String
)
case class AchievementDefinition(achievement: Achievement, predicate: List[UserDrinkEvent] => Boolean)
object AchievementDefinitions {
  private def countTypes(events: List[UserDrinkEvent], drinkType: DrinkType) =
    events.count(_.drink.`type` == drinkType)
  import DrinkType._
  val eventBaseAchievements = List(
    /**
      * Beer
      */

    AchievementDefinition(
      Achievement(
        1,
        "Moe",
        "1 Bier bestellt",
        "/internal/assets/achievements/moe.png"
      ),
      (events: List[UserDrinkEvent]) => events.exists(_.drink.`type` == BEER)
    ),
    AchievementDefinition(
      Achievement(
        2,
        "Lenny",
        "5 Bier bestellt",
        "/internal/assets/achievements/lenny.png"
      ),
      (events: List[UserDrinkEvent]) => events.count(_.drink.`type` == BEER) >= 5
    ),
    AchievementDefinition(
      Achievement(
        3,
        "Carl",
        "10 Bier bestellt",
        "/internal/assets/achievements/carl.png"
      ),
      (events: List[UserDrinkEvent]) => events.count(_.drink.`type` == BEER) >= 10
    ),
    AchievementDefinition(
      Achievement(4, "Homer", "15 Bier bestellt", "/internal/assets/achievements/homer.png"),
      (events: List[UserDrinkEvent]) => events.count(_.drink.`type` == BEER) >= 15
    ),
    AchievementDefinition(
      Achievement(5, "Barney", "25 Bier bestellt", "/internal/assets/achievements/barney.png"),
      (events: List[UserDrinkEvent]) => events.count(_.drink.`type` == BEER) >= 25
    ),
    /**
      * Cocktails
      */

    AchievementDefinition(
      Achievement(6, "Jeff Lebowski", "1 Cocktails bestellt", "/internal/assets/achievements/derdude.jpg"),
      (events: List[UserDrinkEvent]) => events.count(_.drink.`type` == COCKTAIL) >= 1
    ),
    AchievementDefinition(
      Achievement(7, "Ernest Hemingway", "5 Cocktails bestellt", "/internal/assets/achievements/hemingway.jpg"),
      (events: List[UserDrinkEvent]) => events.count(_.drink.`type` == COCKTAIL) >= 5
    ),
    AchievementDefinition(
      Achievement(8, "Winston Churchill", "10 Cocktails bestellt", "/internal/assets/achievements/churchill.jpg"),
      (events: List[UserDrinkEvent]) => events.count(_.drink.`type` == COCKTAIL) >= 10
    ),
    AchievementDefinition(
      Achievement(9, "George R.R. Martin", "15 Cocktails bestellt", "/internal/assets/achievements/georgerrmartin.jpg"),
      (events: List[UserDrinkEvent]) => events.count(_.drink.`type` == COCKTAIL) >= 15
    ),
    /**
      * Softdrinks
      */
    AchievementDefinition(
      Achievement(10,
                  "Is this just Fanta Sea?",
                  "Mindestens 5 Softdrinks bestellt",
                  "/internal/assets/achievements/fantaSea.jpg"),
      (events: List[UserDrinkEvent]) => events.count(_.drink.`type` == SOFTDRINK) >= 5
    ),
    AchievementDefinition(
      Achievement(11,
                  "Coca Cola Fanta Sprite",
                  "Mindestens 10 Softdrinks bestellt",
                  "/internal/assets/achievements/cocacolaFantaSprite.jpg"),
      (events: List[UserDrinkEvent]) => events.count(_.drink.`type` == SOFTDRINK) >= 10
    ),
    /**
      * Shotrunden
      */
    AchievementDefinition(
      Achievement(12,
                  "Die nächste Runde geht auf mich",
                  "Mindestens 10 Shots auf einmal bestellt",
                  "/internal/assets/achievements/dienaechsterundegehtaufmich.jpg"),
      (events: List[UserDrinkEvent]) => events.last.amount > 10 && events.last.drink.`type` == SHOT
    ),
    AchievementDefinition(
      Achievement(13,
                  "'ne Runde für alle!",
                  "Mindestens 20 Shots auf einmal bestellt",
                  "/internal/assets/achievements/nerundefueralle.jpg"),
      (events: List[UserDrinkEvent]) => events.last.amount > 20 && events.last.drink.`type` == SHOT
    ),
    /**
      * Timing
      */
//    //Der frühe Vogel trinkt Bier
//    // Der abend kann kommen
//    //Einer der letzten Kunden
//
    /**
      * Einmalig
      */
//    AchievementDefinition(
//      Achievement(14, "Glückspils", "25. Bier bestellt", "/internal/assets/achievements/glueckspils.png"),
//      List((BEER, ALL) countEquals 25)
//    ),
//    AchievementDefinition(
//      Achievement(15, "Es geht seinen Gang", "50. Bier bestellt", "/internal/assets/achievements/esgehtseinengang.png"),
//      List((BEER, ALL) countEquals 50)
//    ),
//    AchievementDefinition(
//      Achievement(16, "Veni Vidi Bieri", "100. Bier bestellt", "/internal/assets/achievements/venividibieri.png"),
//      List((BEER, ALL) countEquals 100)
//    ),
//    AchievementDefinition(
//      Achievement(17, "Halbzeit", "150. Bier bestellt", "/internal/assets/achievements/halbzeit.png"),
//      List((BEER, ALL) countEquals 150)
//    ),
//    AchievementDefinition(
//      Achievement(18, "This is Sparta!", "300. Bier bestellt", "/internal/assets/achievements/thisissparta.png"),
//      List((BEER, ALL) countEquals 300)
//    ),
//    /**
//      * Mix
//      */
    AchievementDefinition(
      Achievement(19,
                  "Rauf und runter",
                  "Jeweils ein Bier und ein Cocktail bestellt",
                  "/internal/assets/achievements/raufUndRunter.jpg"),
      (events: List[UserDrinkEvent]) =>
        events.exists(_.drink.`type` == BEER) &&
          events.exists(_.drink.`type` == COCKTAIL)
    ),
    AchievementDefinition(
      Achievement(20,
                  "Abenteurer",
                  "Jeweils fünf Biere und Cocktails bestellt",
                  "/internal/assets/achievements/abenteurer.jpg"),
      (events: List[UserDrinkEvent]) =>
        events.count(_.drink.`type` == BEER) >= 5 &&
          events.count(_.drink.`type` == COCKTAIL) >= 5
    ),
    AchievementDefinition(
      Achievement(21,
                  "Der Alles-Trinker",
                  "Jeweils 10 Biere und Cocktails bestellt",
                  "/internal/assets/achievements/derallestrinker.jpg"),
      (events: List[UserDrinkEvent]) =>
        events.count(_.drink.`type` == BEER) >= 10 &&
          events.count(_.drink.`type` == COCKTAIL) >= 10
    ),
    AchievementDefinition(
      Achievement(22,
                  "Herrengedeck",
                  "Nen Bier und nen Kurzen bestellt",
                  "/internal/assets/achievements/herrengedeck.jpg"),
      (events: List[UserDrinkEvent]) =>
        events.exists(_.drink.`type` == BEER) &&
          events.exists(_.drink.`type` == SHOT)
    ),
    /**
      * Drink specific
      */

    AchievementDefinition(
      Achievement(23,
                  "Luftalarm",
                  "Mindestens 5 Berliner Luft bestellt",
                  "/internal/assets/achievements/luftalarm.jpg"),
      (events: List[UserDrinkEvent]) => events.last.amount > 5 && events.last.drink.name == "Berliner Luft"
    ),
    AchievementDefinition(
      Achievement(24, "Zombieland", "Mindestens 3 Zombies bestellt", "/internal/assets/achievements/zombieland.jpg"),
      (events: List[UserDrinkEvent]) => events.count(_.drink.name == "Zombie") >= 3
    ),
    AchievementDefinition(
      Achievement(
        25,
        "...den trinkt man auf Long Island so",
        "Mindestens einen Long Island Iced Tea bestellt",
        "/internal/assets/achievements/dentrinktmanauflongislandso.jpg"
      ),
      (events: List[UserDrinkEvent]) => events.count(_.drink.name == "Long Island Iced Tea") >= 1
    ),
    AchievementDefinition(
      Achievement(
        26,
        "Anwärter des B.R.A.rabischen Frühlings",
        "Einen Long Island Iced Tea, einen Zombie und ein Bier bestellt",
        "/internal/assets/achievements/pseudoadmin.png"
      ),
      (events: List[UserDrinkEvent]) =>
        events.count(_.drink.name == "Long Island Iced Tea") >= 1 &&
          events.count(_.drink.name == "Zombie") >= 1 &&
          events.exists(_.drink.`type` == BEER)
    )
  )

}
