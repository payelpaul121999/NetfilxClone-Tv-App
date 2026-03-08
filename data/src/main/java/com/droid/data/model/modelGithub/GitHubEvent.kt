package com.droid.data.model.modelGithub

import kotlinx.serialization.Serializable

@Serializable
data class GitHubEvent(
    val id: String,
    val type: String,
    val actor: Actor? = null,
    val repo: Repo? = null,
    val payload: Payload? = null ,
    val public: Boolean,
    val created_at: String,
    val org: Org? = null
)
@Serializable
data class Actor(
    val id: Long,
    val login: String,
    val display_login: String,
    val gravatar_id: String,
    val url: String,
    val avatar_url: String
)
@Serializable
data class Repo(
    val id: Long,
    val name: String,
    val url: String
)
@Serializable
data class Payload(
    val repository_id: Long? = null,
    val push_id: Long? = null,
    val ref: String? = null,
    val head: String? = null,
    val before: String? = null
)
@Serializable
data class Org(
    val id: Long,
    val login: String,
    val gravatar_id: String,
    val url: String,
    val avatar_url: String
)