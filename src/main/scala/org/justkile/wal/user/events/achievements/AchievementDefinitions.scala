package org.justkile.wal.user.events.achievements

import java.time.LocalDateTime

import org.justkile.wal.drinks.domain.DrinkType
import org.justkile.wal.drinks.domain.DrinkType.DrinkType
import org.justkile.wal.user.domain.UserDrinkEvent

case class Achievement(
    id: Int,
    name: String,
    description: String,
    imagePath: String
)
case class AchievementDefinition[T](achievement: Achievement, predicate: T => Boolean)
object AchievementDefinitions {
  private def countTypes(events: List[UserDrinkEvent], drinkType: DrinkType) =
    events.filter(_.drink.`type` == drinkType).foldLeft(0)(_ + _.amount)
  import DrinkType._
  val spaceInvadersScoreAchievements = List(
    AchievementDefinition(
      Achievement(
        1000,
        "Rookie",
        "min. 1000 Punkte in Space Invaders erspielt",
        "/images/new_achievements/rookie.jpg"
      ),
      (score: Long) => score >= 1000
    ),
    AchievementDefinition(
      Achievement(
        1001,
        "Laser Powered Alien Smasher!",
        "min. 5000 Punkte in Space Invaders erspielt",
        "/images/new_achievements/laser_powered_alien_smasher.jpg"
      ),
      (score: Long) => score >= 5000
    ),
    AchievementDefinition(
      Achievement(
        1002,
        "Beyond the Galactic Terror Vortex",
        "min. 10000 Punkte in Space Invaders erspielt",
        "/images/new_achievements/beyond_the_galactic_terror_vortex.jpg"
      ),
      (score: Long) => score >= 10000
    ),
    AchievementDefinition(
      Achievement(
        1003,
        "Defender of the Darkstorm Galaxy",
        "min. 20000 Punkte in Space Invaders erspielt",
        "/images/new_achievements/darkstorm_galaxy.jpg"
      ),
      (score: Long) => score >= 20000
    )
  )
  val eventBaseAchievements = List(
    /**
      * Beer
      */

    AchievementDefinition(
      Achievement(
        1,
        "Zoidberg",
        "1 Bier bestellt",
        "/images/new_achievements/zoidberg.jpg"
      ),
      (events: List[UserDrinkEvent]) => events.exists(_.drink.`type` == BEER)
    ),
    AchievementDefinition(
      Achievement(
        2,
        "Prof. Farnsworth",
        "5 Bier bestellt",
        "/images/new_achievements/proffarnsworth.jpg"
      ),
      (events: List[UserDrinkEvent]) => countTypes(events, BEER) >= 5
    ),
    AchievementDefinition(
      Achievement(
        3,
        "Fry",
        "10 Bier bestellt",
        "/images/new_achievements/fry.png"
      ),
      (events: List[UserDrinkEvent]) => countTypes(events, BEER) >= 10
    ),
    AchievementDefinition(
      Achievement(4, "Leela", "15 Bier bestellt", "/images/new_achievements/leela.png"),
      (events: List[UserDrinkEvent]) => countTypes(events, BEER) >= 15
    ),
    AchievementDefinition(
      Achievement(5, "Bender", "20 Bier bestellt", "/images/new_achievements/bender.jpg"),
      (events: List[UserDrinkEvent]) => countTypes(events, BEER) >= 20
    ),
    /**
      * Cocktails
      */

    AchievementDefinition(
      Achievement(6, "Jean Luc Picard", "1 Cocktail bestellt", "/images/new_achievements/picard.jpg"),
      (events: List[UserDrinkEvent]) => countTypes(events, COCKTAIL) >= 1
    ),
    AchievementDefinition(
      Achievement(7, "James T. Kirk", "5 Cocktails bestellt", "/images/new_achievements/kirk.jpg"),
      (events: List[UserDrinkEvent]) => countTypes(events, COCKTAIL) >= 5
    ),
    AchievementDefinition(
      Achievement(8, "The Doctor", "10 Cocktails bestellt", "/images/new_achievements/doctor.jpg"),
      (events: List[UserDrinkEvent]) => countTypes(events, COCKTAIL) >= 10
    ),
    AchievementDefinition(
      Achievement(9, "Kathryn Janeway", "15 Cocktails bestellt", "/images/new_achievements/janeway.jpeg"),
      (events: List[UserDrinkEvent]) => countTypes(events, COCKTAIL) >= 15
    ),
    AchievementDefinition(
      Achievement(10, "Rick Sanchez", "20 Cocktails bestellt", "/images/new_achievements/rick.jpg"),
      (events: List[UserDrinkEvent]) => countTypes(events, COCKTAIL) >= 20
    ),
    /**
      * Softdrinks
      */
    AchievementDefinition(
      Achievement(11, "Morty", "Mindestens 1 Softdrink bestellt", "/images/new_achievements/morty.jpeg"),
      (events: List[UserDrinkEvent]) => countTypes(events, SOFTDRINK) >= 1
    ),
    AchievementDefinition(
      Achievement(12, "Marvin", "Mindestens 5 Softdrinks bestellt", "/images/new_achievements/marvin.jpg"),
      (events: List[UserDrinkEvent]) => countTypes(events, SOFTDRINK) >= 5
    ),
    AchievementDefinition(
      Achievement(13, "C3PO", "Mindestens 10 Softdrinks bestellt", "/images/new_achievements/c3po.jpeg"),
      (events: List[UserDrinkEvent]) => countTypes(events, SOFTDRINK) >= 10
    ),
    AchievementDefinition(
      Achievement(14, "Ewok", "Mindestens 15 Softdrinks bestellt", "/images/new_achievements/ewok.png"),
      (events: List[UserDrinkEvent]) => countTypes(events, SOFTDRINK) >= 15
    ),
    AchievementDefinition(
      Achievement(15, "Jar Jar Binks", "Mindestens 20 Softdrinks bestellt", "/images/new_achievements/jarjarbinks.png"),
      (events: List[UserDrinkEvent]) => countTypes(events, SOFTDRINK) >= 20
    ),
    /**
      * Shotrunden
      */
    AchievementDefinition(
      Achievement(16,
                  "Initiator einer Interstellaren Versammlung",
                  "Mindestens 10 Shots auf einmal bestellt",
                  "/images/new_achievements/interstellare_versammlung.jpg"),
      (events: List[UserDrinkEvent]) => events.last.amount > 10 && events.last.drink.`type` == SHOT
    ),
    /**
      * Timing
      */
//    //Der frühe Vogel trinkt Bier
    AchievementDefinition(
      Achievement(17,
                  "Zeit ist relativ",
                  "Ein Bier zwischen 8 und 12 Uhr morgens",
                  "/images/new_achievements/zeit_ist_relativ.jpg"),
      (events: List[UserDrinkEvent]) => {
        val eight = LocalDateTime.now().withHour(8)
        val twelve = LocalDateTime.now().withHour(12)
        events.last.createdAt.isAfter(eight) && events.last.createdAt
          .isBefore(twelve) && events.last.drink.`type` == BEER
      }
    ),
//    // Der abend kann kommen
//    //Einer der letzten Kunden
//
    /**
      * Einmalig
      */
//    AchievementDefinition(
//      Achievement(14, "Glückspils", "25. Bier bestellt", "/images/new_achievements/glueckspils.png"),
//      List((BEER, ALL) countEquals 25)
//    ),
//    AchievementDefinition(
//      Achievement(15, "Es geht seinen Gang", "50. Bier bestellt", "/images/new_achievements/esgehtseinengang.png"),
//      List((BEER, ALL) countEquals 50)
//    ),
//    AchievementDefinition(
//      Achievement(16, "Veni Vidi Bieri", "100. Bier bestellt", "/images/new_achievements/venividibieri.png"),
//      List((BEER, ALL) countEquals 100)
//    ),
//    AchievementDefinition(
//      Achievement(17, "Halbzeit", "150. Bier bestellt", "/images/new_achievements/halbzeit.png"),
//      List((BEER, ALL) countEquals 150)
//    ),
//    AchievementDefinition(
//      Achievement(18, "This is Sparta!", "300. Bier bestellt", "/images/new_achievements/thisissparta.png"),
//      List((BEER, ALL) countEquals 300)
//    ),
//    /**
//      * Mix
//      */
    AchievementDefinition(
      Achievement(18,
                  "Rauf und runter",
                  "Jeweils ein Bier und ein Cocktail bestellt",
                  "/images/achievements/raufUndRunter.jpg"),
      (events: List[UserDrinkEvent]) =>
        events.exists(_.drink.`type` == BEER) &&
          events.exists(_.drink.`type` == COCKTAIL)
    ),
    AchievementDefinition(
      Achievement(19, "Abenteurer", "Jeweils fünf Biere und Cocktails bestellt", "/images/achievements/abenteurer.jpg"),
      (events: List[UserDrinkEvent]) =>
        countTypes(events, BEER) >= 5 &&
          countTypes(events, COCKTAIL) >= 5
    ),
    AchievementDefinition(
      Achievement(20,
                  "Der Alles-Trinker",
                  "Jeweils 10 Biere und Cocktails bestellt",
                  "/images/achievements/derallestrinker.jpg"),
      (events: List[UserDrinkEvent]) =>
        countTypes(events, BEER) >= 10 &&
          countTypes(events, COCKTAIL) >= 10
    ),
    AchievementDefinition(
      Achievement(21, "Glorb Gedeck", "Nen Bier und nen Kurzen bestellt", "/images/new_achievements/glorbgedeck.jpg"),
      (events: List[UserDrinkEvent]) =>
        events.exists(_.drink.`type` == BEER) &&
          events.exists(_.drink.`type` == SHOT)
    ),
    /**
      * Drink specific
      */

    AchievementDefinition(
      Achievement(22,
                  "Luftdruck wiederhergestellt",
                  "Mindestens 5 Berliner Luft bestellt",
                  "/images/new_achievements/luftdruck.jpg"),
      (events: List[UserDrinkEvent]) => events.last.amount > 5 && events.last.drink.name == "Berliner Luft"
    ),
    AchievementDefinition(
      Achievement(23,
                  "Space Zombieland",
                  "Mindestens 3 Zombies bestellt",
                  "/images/new_achievements/space_zombies.jpg"),
      (events: List[UserDrinkEvent]) => events.count(_.drink.name == "Space Zombie") >= 3
    ),
    AchievementDefinition(
      Achievement(
        24,
        "...den trinkt man auf Deep Space Nine so",
        "Mindestens einen Deep Space Nine Iced Tea bestellt",
        "/images/new_achievements/deep_space_nine.jpg"
      ),
      (events: List[UserDrinkEvent]) => events.count(_.drink.name == "Deep Space 9 Iced Tea") >= 1
    ),
    AchievementDefinition(
      Achievement(
        25,
        "Anwärter des galaktischen B.R.A.rabischen Frühlings",
        "Einen Deep Space 9 Iced Tea, einen Space Zombie und ein Bier bestellt",
        "/images/achievements/pseudoadmin.png"
      ),
      (events: List[UserDrinkEvent]) =>
        events.count(_.drink.name == "Deep Space 9 Iced Tea") >= 1 &&
          events.count(_.drink.name == "Space Zombie") >= 1 &&
          events.exists(_.drink.`type` == BEER)
    ),
    AchievementDefinition(
      Achievement(
        26,
        "Er ist tot, Jim",
        "1x Bloody Red Shirt bestellt",
        "/images/new_achievements/hes-dead-jim.jpg"
      ),
      (events: List[UserDrinkEvent]) => events.count(_.drink.name == "Bloody Red Shirt") >= 1
    ),
    AchievementDefinition(
      Achievement(
        27,
        "Scheiss aufs Universssum!",
        "1x KontinuumstransMojitor oder 1x KontinuumstransMockitor bestellt. Dies ist ein sehr mächtiges und rätselhaftes Gerät dessen Rätselhaftigkeit nur durch seine Macht übertroffen wird.",
        "/images/new_achievements/scheiss_aufs_universssum.jpg"
      ),
      (events: List[UserDrinkEvent]) =>
        events.count(_.drink.name == "KontinuumstransMojitor") >= 1 || events.count(
          _.drink.name == "KontinuumstransMockitor") >= 1
    ),
    AchievementDefinition(
      Achievement(
        28,
        "Weltraumgurke!",
        "1x LeCosmoGurque bestellt",
        "/images/new_achievements/weltraumgurke.jpg"
      ),
      (events: List[UserDrinkEvent]) => events.count(_.drink.name == "LeCosmoGurque") >= 1
    ),
    AchievementDefinition(
      Achievement(
        29,
        "Glorb Attack!",
        "1x White Glorb bestellt",
        "/images/new_achievements/glorb_attack.jpeg"
      ),
      (events: List[UserDrinkEvent]) => events.count(_.drink.name == "White Glorb") >= 1
    ),
    AchievementDefinition(
      Achievement(
        30,
        "Plan 9 from outer Space",
        "1x Space Zombie",
        "/images/new_achievements/plan9_from_outer_space.jpg"
      ),
      (events: List[UserDrinkEvent]) => events.count(_.drink.name == "Space Zombie") >= 1
    ),
    AchievementDefinition(
      Achievement(
        31,
        "Deine Mudder is dein Erzeuger!",
        "1x Dein Erzeuger bestellt",
        "/images/new_achievements/deine_mudder.jpg"
      ),
      (events: List[UserDrinkEvent]) => events.count(_.drink.name == "Dein Erzeuger") >= 1
    ),
    AchievementDefinition(
      Achievement(
        32,
        "Ob die jemals fliegt?",
        "1x Polnische Rakede",
        "/images/new_achievements/polnische_rakete.jpg"
      ),
      (events: List[UserDrinkEvent]) => events.count(_.drink.name == "Polnische Rakede") >= 1
    ),
    AchievementDefinition(
      Achievement(
        33,
        "Und er ist doch ein Planet! Free Pluto!",
        "1x Pluto Libre bestellt ",
        "/images/new_achievements/pluto.jpg"
      ),
      (events: List[UserDrinkEvent]) => events.count(_.drink.name == "Pluto Libre") >= 1
    ),
    AchievementDefinition(
      Achievement(
        34,
        "Einmal durchs Sonnensystem",
        "1x Mercury Mule, 1x Mars Tai, 1x Pluto Libre",
        "/images/new_achievements/einmal_durchs_sonennsystem.jpg"
      ),
      (events: List[UserDrinkEvent]) =>
        events.count(_.drink.name == "Pluto Libre") >= 1 &&
          events.count(_.drink.name == "Mars Tai") >= 1 &&
          events.count(_.drink.name == "Mercury Mule") >= 1
    ),
    AchievementDefinition(
      Achievement(
        35,
        "Hol den gleichen Drink nochmal!",
        "3x denselben Drink bestellt",
        "/images/new_achievements/hol_den_selben_drink_nochmal.jpg"
      ),
      (events: List[UserDrinkEvent]) => {
        val eventsSize = events.size
        eventsSize >= 3 &&
        events.last.drink.id == events(eventsSize - 2).drink.id &&
        events(eventsSize - 2).drink.id == events(eventsSize - 3).drink.id
      }
    ),
    AchievementDefinition(
      Achievement(
        36,
        "Der Alte Mann und der Weltraum",
        "1x Hemingways Astro Sour",
        "/images/new_achievements/der_alte_mann_und_der_weltraum.jpg"
      ),
      (events: List[UserDrinkEvent]) => events.count(_.drink.name == "Hemingways Astro Sour") >= 1
    )
  )

}
