package com.droid.data.model.authModel

import com.droid.data.model.User
import com.droid.data.model.error.ApiMessageModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val accountActive: String ?=null,
    val apiKey: String ? = null,
    val userEmail: String?=null,
    val userMobile: String? = null,
    val userName: String?=null
) : ApiMessageModel()

fun RegisterResponse.toUser() = User(
    name = userName.orEmpty(),
    userEmail = userEmail.orEmpty(),
    userMobile = userMobile.orEmpty()
)
