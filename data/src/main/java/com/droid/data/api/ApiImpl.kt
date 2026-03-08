package com.droid.data.api

import android.util.Log
import com.droid.data.api.ApiRoutes.SEARCH_USERS
import com.droid.data.api.ApiRoutes.SEARCH_USERS_BY_NAME
import com.droid.data.api.ApiRoutes.SEARCH_USERS_EVENT
import com.droid.data.model.modelGithub.GitHubEvent
import com.droid.data.model.modelGithub.GitHubUser
import com.droid.data.model.modelGithub.UserSearchResponse

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.header
import io.ktor.client.request.url
import kotlinx.serialization.json.Json


class ApiImpl(
    private val client: HttpClient
) : Api {
    override suspend fun getEvents(
        perPage: Int,
        page: Int
    ): List<GitHubEvent>? {

        return try {
            val response: String = client.get {
                url(SEARCH_USERS_EVENT)
                parameter("per_page", perPage)
                parameter("page", page)
                header("User-Agent", "Ktor-App")
            }
            Log.e("@@@USER", response)
            Json { ignoreUnknownKeys = true }
                .decodeFromString(response)
        } catch (e: Exception) {
            null
        }
    }
    override suspend fun getUser(username: String): GitHubUser? {
        return try {
            val response: String = client.get {
                url(SEARCH_USERS_BY_NAME+username)
                header("User-Agent", "Ktor-App")
            }
            Json { ignoreUnknownKeys = true }
                .decodeFromString(response)
        } catch (e: Exception) {
            Log.e("@@@USER", e.stackTraceToString())
            null
        }
    }
    override suspend fun searchUsers(
        query: String,
        page: Int,
        perPage: Int
    ): UserSearchResponse? {

        return try {
            val response: String = client.get {
                url(SEARCH_USERS)
                parameter("q", query)
                parameter("page", page)
                parameter("per_page", perPage)
                header("User-Agent", "Ktor-App")
            }
            Json { ignoreUnknownKeys = true }
                .decodeFromString(response)

        } catch (e: Exception) {
            Log.e("@@@SEARCH", e.stackTraceToString())
            null
        }
    }
}