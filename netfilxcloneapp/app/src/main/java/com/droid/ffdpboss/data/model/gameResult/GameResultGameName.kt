package com.droid.ffdpboss.data.model.gameResult

import com.droid.data.model.resultModel.MasterGame

data class GameResultGameName(
    val gameId: String,
    val gameName: String,
    val gameDescription: String,
    val gameImage: String,
)

fun List<MasterGame>.toGameResultGameNames() = map {
    GameResultGameName(
        gameId = it.gameSl.orEmpty(),
        gameName = it.gameName.orEmpty(),
        gameDescription = it.gameTitle.orEmpty(),
        gameImage = it.gamePhotoPath.orEmpty(),
    )
}
