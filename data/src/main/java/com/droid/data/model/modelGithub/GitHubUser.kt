package com.droid.data.model.modelGithub

import kotlinx.serialization.Serializable

@Serializable
data class GitHubUser(
    val login: String,
    val name: String? = null,
    val avatar_url: String,
    val bio: String? = null
)
@Serializable
data class UserSearchResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<UserItem>
)

@Serializable
data class UserItem(
    val login: String,
    val id: Long,
    val avatar_url: String,
    val type: String,
    val score: Double
)