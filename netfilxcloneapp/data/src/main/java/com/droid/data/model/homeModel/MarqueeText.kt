package com.droid.data.model.homeModel


import com.droid.data.model.error.ApiMessageModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarqueeText(
    @SerialName("marqueeText")
    val marqueeText: String
) : ApiMessageModel()