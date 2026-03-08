package com.droid.data.model.homeModel.masterGameBajiModel.gameTypeResponse


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MasterGameBajiType(
    @SerialName("bajiTypeName")
    val bajiTypeName: String,

    @SerialName("photoPath")
    val photoPath: String,

    @SerialName("typeStat")
    val typeStat: String
)