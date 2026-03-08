package com.droid.data.model.resultModel

import kotlinx.serialization.Serializable

@Serializable
data class GameResultDTO(
    val Message: String,
    val Status: Boolean,
    val masterGameList: List<MasterGame>
)