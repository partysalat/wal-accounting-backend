package org.justkile.wal.projections.domain

import org.justkile.wal.core.achievements.Achievement

case class BestlistUserStats(id: Int,
                             userId: String,
                             beerCount: Int,
                             cocktailCount: Int,
                             softdrinkCount: Int,
                             shotCount: Int,
                             spaceInvadersScore: Long,
                             user: UserProjection,
                             achievements: List[Achievement] = List.empty)
