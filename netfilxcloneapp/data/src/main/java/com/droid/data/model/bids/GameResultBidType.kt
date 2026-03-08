package com.droid.data.model.bids




data class GameResultBidType(
    val gameId: Int,
    val gameType: String,
    val results: List<GameBidResultitem>,
    var isSelected: Boolean = false
)

fun List<BidGameType>.toBidType() = mapIndexed { index, gameType ->
    GameResultBidType(
        gameId = gameType.id ?: 1,
        gameType = gameType.type.orEmpty(),
        results = gameType.results.orEmpty(),
        isSelected = index == 0
    )
}