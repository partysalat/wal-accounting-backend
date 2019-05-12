package org.justkile.wal.user.domain

import org.justkile.wal.user.events.achievements.Achievement

case class BestlistUserStats(id: Int,
                             userId: String,
                             beerCount: Int,
                             cocktailCount: Int,
                             softdrinkCount: Int,
                             shotCount: Int,
                             spaceInvadersScore: Long,
                             user: UserProjection,
                             achievements: List[Achievement] = List.empty)
