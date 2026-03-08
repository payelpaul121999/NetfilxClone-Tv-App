package com.droid.data.model.resultModel

import kotlinx.serialization.Serializable

@Serializable
data class GameType(
    val id: Int?=null,
    val results: List<GameResultItem>?=null,
    val type: String?=null
)