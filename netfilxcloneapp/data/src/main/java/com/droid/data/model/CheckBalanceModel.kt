package com.droid.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckBalanceModel(
    @SerialName("Message")
    val message: String,
    @SerialName("Status")
    val status: Boolean,
    @SerialName("walletBalance")
    val walletBalance: String
)