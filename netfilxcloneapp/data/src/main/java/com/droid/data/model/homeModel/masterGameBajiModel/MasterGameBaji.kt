package com.droid.data.model.homeModel.masterGameBajiModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MasterGameBaji(
    @SerialName("bajiName")
    val bajiName: String,
    @SerialName("bajiStat")
    val bajiStat: Int,
    @SerialName("closeTime")
    val closeTime: String,
    @SerialName("gameMasterBajiSl")
    val gameMasterBajiSl: String,
    @SerialName("openTime")
    val openTime: String
)