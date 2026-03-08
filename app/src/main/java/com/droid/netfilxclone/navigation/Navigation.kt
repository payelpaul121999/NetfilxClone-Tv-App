package com.droid.netfilxclone.navigation


import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.droid.netfilxclone.data.model.ContentRow
import com.droid.netfilxclone.data.model.Movie
import com.droid.netfilxclone.data.repository.NetflixRepository
import com.droid.netfilxclone.viewmodel.HomeViewModel
import com.droid.netfilxclone.ui.components.NetflixSideNav
import com.droid.netfilxclone.ui.screen.HomeScreen
import com.droid.netfilxclone.ui.screen.MovieDetailScreen
import com.droid.netfilxclone.ui.screen.MyListScreen
import com.droid.netfilxclone.ui.screen.ProfileSelectionScreen
import com.droid.netfilxclone.ui.screen.SearchScreen
import org.koin.androidx.compose.koinViewModel

sealed class Screen(val route: String) {
    object ProfileSelection : Screen("profile_selection")
    object Main : Screen("main")
    object Detail : Screen("detail/{movieId}") {
        fun createRoute(movieId: Int) = "detail/$movieId"
    }
}

@Composable
fun NetflixNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ProfileSelection.route,
        modifier = modifier
    ) {
        composable(Screen.ProfileSelection.route) {
            val repository = remember { NetflixRepository() }
            ProfileSelectionScreen(
                profiles = repository.getUserProfiles(),
                onProfileSelected = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.ProfileSelection.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Main.route) {
            MainScreen(navController = navController)
        }
        composable(Screen.Detail.route) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull() ?: return@composable
            val viewModel: HomeViewModel = koinViewModel()
            val homeState by viewModel.homeState.collectAsState()
            val myList by viewModel.myList.collectAsState()
            val movie = homeState.contentRows
                .flatMap { it.movies }
                .firstOrNull { it.id == movieId }
                ?: homeState.heroMovie?.takeIf { it.id == movieId }

            movie?.let {
                MovieDetailScreen(
                    movie = it,
                    isInMyList = myList.any { m -> m.id == it.id },
                    similarMovies = homeState.contentRows.firstOrNull()?.movies?.filter { m -> m.id != it.id }
                        ?.take(8) ?: emptyList(),
                    onPlayClick = {},
                    onBackClick = { navController.popBackStack() },
                    onMyListToggle = { viewModel.toggleMyList(it) },
                    onSimilarMovieClick = { similar ->
                        navController.navigate(Screen.Detail.createRoute(similar.id))
                    }
                )
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    val viewModel: HomeViewModel = koinViewModel()
    val homeState by viewModel.homeState.collectAsState()
    val searchState by viewModel.searchState.collectAsState()
    val myList by viewModel.myList.collectAsState()

    var currentRoute by remember { mutableStateOf("home") }
    var isNavExpanded by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxSize()) {
        // Side Navigation
        NetflixSideNav(
            currentRoute = currentRoute,
            isExpanded = isNavExpanded,
            onRouteSelected = { route ->
                currentRoute = route
                isNavExpanded = false
            }
        )

        // Main Content
        Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
            AnimatedContent(
                targetState = currentRoute,
                transitionSpec = {
                    fadeIn(animationSpec = androidx.compose.animation.core.tween(300)) togetherWith
                            fadeOut(animationSpec = androidx.compose.animation.core.tween(300))
                },
                label = "screen_transition"
            ) { route ->
                when (route) {
                    "home" -> HomeScreen(
                        heroMovie = homeState.heroMovie,
                        contentRows = homeState.contentRows,
                        isLoading = homeState.isLoading,
                        isInMyList = { viewModel.isInMyList(it) },
                        onMovieClick = { movie ->
                            navController.navigate(Screen.Detail.createRoute(movie.id))
                        },
                        onMyListToggle = viewModel::toggleMyList
                    )
                    "tv_shows" -> ContentBrowseScreen(
                        title = "TV Shows",
                        contentRows = homeState.contentRows.take(5),
                        onMovieClick = { movie ->
                            navController.navigate(Screen.Detail.createRoute(movie.id))
                        }
                    )
                    "movies" -> ContentBrowseScreen(
                        title = "Movies",
                        contentRows = homeState.contentRows.drop(3),
                        onMovieClick = { movie ->
                            navController.navigate(Screen.Detail.createRoute(movie.id))
                        }
                    )
                    "my_list" -> MyListScreen(
                        movies = myList,
                        onMovieClick = { movie ->
                            navController.navigate(Screen.Detail.createRoute(movie.id))
                        }
                    )
                    "search" -> SearchScreen(
                        query = searchState.query,
                        results = searchState.results,
                        isSearching = searchState.isSearching,
                        onQueryChange = viewModel::search,
                        onMovieClick = { movie ->
                            navController.navigate(Screen.Detail.createRoute(movie.id))
                        }
                    )
                    else -> HomeScreen(
                        heroMovie = homeState.heroMovie,
                        contentRows = homeState.contentRows,
                        isLoading = homeState.isLoading,
                        isInMyList = { viewModel.isInMyList(it) },
                        onMovieClick = { movie ->
                            navController.navigate(Screen.Detail.createRoute(movie.id))
                        },
                        onMyListToggle = viewModel::toggleMyList
                    )
                }
            }
        }
    }
}

@Composable
fun ContentBrowseScreen(
    title: String,
    contentRows: List<ContentRow>,
    onMovieClick: (Movie) -> Unit
) {
    HomeScreen(
        heroMovie = contentRows.firstOrNull()?.movies?.firstOrNull(),
        contentRows = contentRows,
        isLoading = false,
        isInMyList = { false },
        onMovieClick = onMovieClick,
        onMyListToggle = {}
    )
}