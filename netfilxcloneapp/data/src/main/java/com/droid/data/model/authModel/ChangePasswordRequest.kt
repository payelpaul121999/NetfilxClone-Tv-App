package com.droid.data.model.authModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordRequest(
    @SerialName("apikey")
    val apikey: String,
    @SerialName("password_confirm")
    val passwordConfirm: String,
    @SerialName("password_new")
    val passwordNew: String,
    @SerialName("password_old")
    val passwordOld: String
)