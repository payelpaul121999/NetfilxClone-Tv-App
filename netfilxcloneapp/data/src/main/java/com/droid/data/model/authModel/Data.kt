package com.droid.data.model.authModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("accountActive")
    val accountActive: String,
    @SerialName("apiKey")
    val apiKey: String,
    @SerialName("userEmail")
    val userEmail: String,
    @SerialName("userMobile")
    val userMobile: String,
    @SerialName("userName")
    val userName: String
)
