package com.droid.data.model.homeModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MasterGame(
    @SerialName("gameName")
    val gameName: String,
    @SerialName("gamePhotoPath")
    val gamePhotoPath: String,
    @SerialName("gameSl")
    val gameSl: String,
    @SerialName("gameTitle")
    val gameTitle: String
)