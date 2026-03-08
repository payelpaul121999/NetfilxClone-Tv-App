package com.droid.data.model.homeModel.masterGameBajiModel.gameTypeResponse


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class masterGameBajiTypeResponse(
    @SerialName("masterGameBajiTypeList")
    val masterGameBajiTypeList: List<MasterGameBajiType>?,
    @SerialName("Message")
    val message: String?,
    @SerialName("Status")
    val status: Boolean?
)