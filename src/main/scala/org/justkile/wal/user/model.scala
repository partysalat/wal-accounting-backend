package org.justkile.wal.user

import java.time.ZonedDateTime


object model {

  case class User(
                   name: String,
                   id: Int,
                   createdAt: ZonedDateTime = ZonedDateTime.now(),
                   updatedAt: ZonedDateTime = ZonedDateTime.now())
}
