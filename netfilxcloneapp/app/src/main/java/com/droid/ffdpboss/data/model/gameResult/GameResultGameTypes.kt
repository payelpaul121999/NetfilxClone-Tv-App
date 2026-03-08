package com.droid.ffdpboss.data.model.gameResult

import com.droid.data.model.bids.BidGameType
import com.droid.data.model.resultModel.GameResultItem
import com.droid.data.model.resultModel.GameType

data class GameResultGameType(
    val gameId: Int,
    val gameType: String,
    val results: List<GameResultItem>,
    var isSelected: Boolean = false
)


fun List<GameType>.toGameResultGameTypes() = mapIndexed { index, gameType ->
    GameResultGameType(
        gameId = gameType.id ?: 1,
        gameType = gameType.type.orEmpty(),
        results = gameType.results.orEmpty(),
        isSelected = index == 0
    )
}

