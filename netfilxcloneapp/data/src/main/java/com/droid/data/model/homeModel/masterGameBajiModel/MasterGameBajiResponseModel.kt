package com.droid.data.model.homeModel.masterGameBajiModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MasterGameBajiResponseModel(
    @SerialName("masterGameBajiList")
    val masterGameBajiList: List<MasterGameBaji>?=null,
    @SerialName("Message")
    val Message: String?=null,
    @SerialName("Status")
    val Status: Boolean?=null
)