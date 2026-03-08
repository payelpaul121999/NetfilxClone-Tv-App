package com.droid.netfilxclone.data.model


import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val backdropUrl: String,
    val rating: Float,
    val year: Int,
    val duration: String,
    val genre: List<String>,
    val cast: List<String> = emptyList(),
    val isNew: Boolean = false,
    val isTopTen: Boolean = false,
    val maturityRating: String = "TV-MA",
    val videoUrl: String = ""
)

@Serializable
data class ContentRow(
    val id: Int,
    val title: String,
    val movies: List<Movie>
)

@Serializable
data class Category(
    val id: Int,
    val name: String
)

enum class ContentType {
    MOVIE, TV_SHOW, DOCUMENTARY, ANIME , DRAMA
}

data class UserProfile(
    val id: Int,
    val name: String,
    val avatarColor: Long,
    val isKidsProfile: Boolean = false
)

sealed class NavigationItem(val route: String, val title: String) {
    object Home : NavigationItem("home", "Home")
    object TVShows : NavigationItem("tv_shows", "TV Shows")
    object Movies : NavigationItem("movies", "Movies")
    object MyList : NavigationItem("my_list", "My List")
    object Search : NavigationItem("search", "Search")
}
