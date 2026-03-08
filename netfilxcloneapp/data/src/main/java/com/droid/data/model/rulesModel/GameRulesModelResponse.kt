package com.droid.data.model.rulesModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameRulesModelResponse(
    @SerialName("gameRulesList")
    val gameRulesList: List<GameRules>,
    @SerialName("Message")
    val message: String,
    @SerialName("Status")
    val status: Boolean
)