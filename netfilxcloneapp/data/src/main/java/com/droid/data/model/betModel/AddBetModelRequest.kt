package com.droid.data.model.betModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddBetModelRequest(
    @SerialName("apikey")
    val apikey: String?=null,
    @SerialName("betAmount")
    val betAmount: String?=null,
    @SerialName("digitNo")
    val digitNo: String?=null,
    @SerialName("gameMasterBajiSl")
    val gameMasterBajiSl: String?=null,
    @SerialName("gameMasterBajiType")
    val gameMasterBajiType: String?=null,
    @SerialName("gameMasterSl")
    val gameMasterSl: String?=null
)