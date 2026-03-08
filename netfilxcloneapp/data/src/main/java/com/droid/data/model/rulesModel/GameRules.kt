package com.droid.data.model.rulesModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameRules(
    @SerialName("rulesTitle")
    val rulesTitle: String,
    @SerialName("title1")
    val title1: String,
    @SerialName("title2")
    val title2: String,
    @SerialName("title3")
    val title3: String,
    @SerialName("title4")
    val title4: String,
    @SerialName("title5")
    val title5: String
)