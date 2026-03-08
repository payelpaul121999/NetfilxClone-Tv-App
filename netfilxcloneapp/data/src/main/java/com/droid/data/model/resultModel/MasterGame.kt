package com.droid.data.model.resultModel

import kotlinx.serialization.Serializable

@Serializable
data class MasterGame(
    val gameName: String?=null,
    val gamePhotoPath: String?=null,
    val gameSl: String?=null,
    val gameTitle: String?=null,
    val gameTypes: List<GameType>?=null
)