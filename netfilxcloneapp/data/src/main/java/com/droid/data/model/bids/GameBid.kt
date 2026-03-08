package com.droid.data.model.bids

import com.droid.data.model.resultModel.GameType

data class GameBidGameName(
    val gameId: String,
    val gameName: String,
    val gameDescription: String,
    val gameImage: String,
)

fun List<MasterGame>.toGameResultGameNames() = map {
    GameBidGameName(
        gameId = it.gameSl.orEmpty(),
        gameName = it.gameName.orEmpty(),
        gameDescription = it.gameTitle.orEmpty(),
        gameImage = it.gamePhotoPath.orEmpty(),
    )
}


