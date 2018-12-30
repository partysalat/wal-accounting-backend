package org.justkile.wal.drinks.domain

import doobie.util.meta.Meta
import io.circe.{Decoder, Encoder}
import org.justkile.wal.drinks.domain.DrinkType.DrinkType

object DrinkType extends Enumeration {
  type DrinkType = Value
  val COCKTAIL = Value("COCKTAIL")
  val SHOT = Value("SHOT")
  val BEER = Value("BEER")
  val COFFEE = Value("COFFEE")
  val SOFTDRINK = Value("SOFTDRINK")

  implicit val drinkTypeMeta: Meta[DrinkType] = Meta[String]
    .xmap((s: String) => DrinkType.withName(s), (d: DrinkType) => d.toString)

  implicit val genderDecoder: Decoder[DrinkType] = Decoder.enumDecoder(DrinkType)
  implicit val genderEncoder: Encoder[DrinkType] = Encoder.enumEncoder(DrinkType)
}

case class Drink(id: Int, name: String, `type`: DrinkType)
