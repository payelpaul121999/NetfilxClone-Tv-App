package com.droid.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class User(
    val name: String? = null,
    val userEmail: String? = null,
    val userMobile: String? = null,
)


fun serializeUser(user: User?): String {
    return Json { ignoreUnknownKeys = true }.encodeToString(user)
}

fun deserializeUser(jsonString: String): User? {
    return try {
        Json { ignoreUnknownKeys = true }.decodeFromString(jsonString)
    } catch (e: Exception) {
        null
    }
}

