package com.droid.data.model.authModel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    @SerialName("email_id")
    val emailId: String,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("mobile_no")
    val mobileNo: String,
    @SerialName("password_confirm")
    val passwordConfirm: String,
    @SerialName("password_new")
    val passwordNew: String
)

@Serializable
data class ApiKeyModel(
    @SerialName("apikey")
    val apikey:String)