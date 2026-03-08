package com.droid.data.api


import com.droid.data.model.modelGithub.GitHubEvent
import com.droid.data.model.modelGithub.GitHubUser
import com.droid.data.model.modelGithub.UserSearchResponse


interface Api {
    suspend fun getEvents(
        perPage: Int, page: Int
    ): List<GitHubEvent>?

    suspend fun getUser(username: String): GitHubUser?
    suspend fun searchUsers(
        query: String,
        page: Int,
        perPage: Int
    ): UserSearchResponse?
}