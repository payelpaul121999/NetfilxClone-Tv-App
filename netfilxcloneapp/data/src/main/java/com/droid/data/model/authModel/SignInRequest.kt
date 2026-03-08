package com.droid.data.model.authModel

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val username : String,
    val password : String
)
