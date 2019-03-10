package org.justkile.wal.drinks.http

import cats.Applicative
import cats.effect._
import cats.implicits._
import io.circe.generic.auto._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.justkile.wal.drinks.algebras.DrinkRepository
import org.justkile.wal.drinks.domain.DrinkType
import org.justkile.wal.drinks.domain.DrinkType.DrinkType

class DrinkService[F[_]: Sync: DrinkRepository: Applicative] extends Http4sDsl[F] {
  case class CreateDrinkRequest(name: String, `type`: DrinkType)
  val service: HttpRoutes[F] = HttpRoutes.of[F] {

    case req @ POST -> Root =>
      for {
        createDrinkRequest <- req.as[CreateDrinkRequest]
        res <- DrinkRepository[F].addDrink(createDrinkRequest.name, createDrinkRequest.`type`)
        result <- Created(res)
      } yield result

    case req @ GET -> Root / drinkTypeString =>
      for {
        drinkType <- Applicative[F].pure(DrinkType.withName(drinkTypeString))
        drinks <- DrinkRepository[F].getDrinksForType(drinkType)
        result <- Ok(drinks)
      } yield result
  }
}
