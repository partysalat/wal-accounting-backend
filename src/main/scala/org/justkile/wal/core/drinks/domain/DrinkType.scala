package org.justkile.wal.core.drinks.domain

import doobie.util.meta.Meta
import io.circe.{Decoder, Encoder}

object DrinkType extends Enumeration {
  type DrinkType = Value
  val COCKTAIL = Value("COCKTAIL")
  val SHOT = Value("SHOT")
  val BEER = Value("BEER")
  val SOFTDRINK = Value("SOFTDRINK")

  implicit val drinkTypeMeta: Meta[DrinkType] = Meta[String]
    .xmap((s: String) => DrinkType.withName(s), (d: DrinkType) => d.toString)

  implicit val drinkTypeDecoder: Decoder[DrinkType] = Decoder.enumDecoder(DrinkType)
  implicit val drinkTypeEncoder: Encoder[DrinkType] = Encoder.enumEncoder(DrinkType)
}
