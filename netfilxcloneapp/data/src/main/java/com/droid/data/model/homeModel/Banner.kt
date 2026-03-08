package com.droid.data.model.homeModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Banner(
    @SerialName("bannerTitle")
    val bannerTitle: String,
    @SerialName("bannerUnqID")
    val bannerUnqID: String,
    @SerialName("photoPath")
    val photoPath: String,
    @SerialName("photoRank")
    val photoRank: String
)