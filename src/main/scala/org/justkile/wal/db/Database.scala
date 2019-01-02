package org.justkile.wal.db

import cats.effect.IO
import cats.implicits._
import doobie.implicits._
import doobie.util.transactor.Transactor

object Database {
  val xa: Transactor[IO] = Transactor.fromDriverManager[IO](
    "org.h2.Driver",
    "jdbc:h2:./db.h2:test;DB_CLOSE_DELAY=-1",
    "sa",
    ""
  )

  val schemaDefinition: IO[Unit] = List(
    sql"""
      CREATE TABLE IF NOT EXISTS events (
      id         INT AUTO_INCREMENT PRIMARY KEY,
      identifier VARCHAR,
      sequence   INT,
      event      BINARY
    )
    """.update.run,
    sql"""
      CREATE TABLE IF NOT EXISTS users (
      id       INT AUTO_INCREMENT PRIMARY KEY,
      userId   VARCHAR UNIQUE NOT NULL,
      name     VARCHAR UNIQUE
    )
    """.update.run,
    sql"""
      CREATE TABLE IF NOT EXISTS drinks (
        id        INT AUTO_INCREMENT PRIMARY KEY,
        drinkName VARCHAR NOT NULL,
        drinkType VARCHAR NOT NULL
    )
    """.update.run,
    sql"""
      CREATE TABLE IF NOT EXISTS news (
        id        INT AUTO_INCREMENT PRIMARY KEY,
        newsType VARCHAR NOT NULL,
        userId VARCHAR NOT NULL,
        amount INT NOT NULL,
        referenceId INT NOT NULL
    )
    """.update.run
  ).traverse_(_.transact(xa))
  //id: String,
  //newsType: NewsType,
  //userId: String,
  //amount: Int,
  //referenceId: Int
//  val insertions: IO[Unit] = List(
//    sql"""
//      INSERT INTO user (id, username, email)
//      VALUES  (0, 'Luka', 'luka.jacobowitz@gmail.com');
//    """.update.run,
//    sql"""
//      INSERT INTO user (id, username, email)
//      VALUES  (1, 'Typelevel', 'info@typelevel.org');
//    """.update.run,
//    sql"""
//      INSERT INTO project (id, name, description, owner)
//      VALUES  (0, 'Cats', 'Functional abstractions for Scala', 1);
//    """.update.run
//  ).traverse_(_.transact(xa))
}
