package com.droid.data.model.homeModel.masterGameBajiModel.requestModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MasterGameTypeRequest(
    @SerialName("apikey")
    val apikey: String,
    @SerialName("gameMasterBajiSl")
    val gameMasterBajiSl: String,
    @SerialName("gameMasterSl")
    val gameMasterSl: String
)