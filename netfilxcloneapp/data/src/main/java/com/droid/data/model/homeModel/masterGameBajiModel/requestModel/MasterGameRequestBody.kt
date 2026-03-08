package com.droid.data.model.homeModel.masterGameBajiModel.requestModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MasterGameRequestBody(
    @SerialName("apikey")
    val apikey: String,
    @SerialName("gameMasterSl")
    val gameMasterSl: String
)