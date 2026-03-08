package com.droid.netfilxclone.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droid.netfilxclone.data.repository.NetflixRepository
import com.droid.netfilxclone.data.model.ContentRow
import com.droid.netfilxclone.data.model.Movie

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// ─────────────────────────────────────────────────────────────────────────────
// UI State models
// ─────────────────────────────────────────────────────────────────────────────

data class HomeUiState(
    val isLoading: Boolean = true,
    val heroMovie: Movie? = null,
    val contentRows: List<ContentRow> = emptyList(),
    val error: String? = null
)

data class SearchUiState(
    val query: String = "",
    val results: List<Movie> = emptyList(),
    val isSearching: Boolean = false
)

// ─────────────────────────────────────────────────────────────────────────────
// ViewModel
// ─────────────────────────────────────────────────────────────────────────────

class HomeViewModel(
    private val repository: NetflixRepository
) : ViewModel() {

    // ── Exposed state ─────────────────────────────────────────────────────────

    private val _homeState = MutableStateFlow(HomeUiState())
    val homeState: StateFlow<HomeUiState> = _homeState.asStateFlow()

    private val _searchState = MutableStateFlow(SearchUiState())
    val searchState: StateFlow<SearchUiState> = _searchState.asStateFlow()

    private val _myList = MutableStateFlow<List<Movie>>(emptyList())
    val myList: StateFlow<List<Movie>> = _myList.asStateFlow()

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie.asStateFlow()

    // ── Init ──────────────────────────────────────────────────────────────────

    init {
        loadHeroMovie()
        loadContentRows()
        loadMyList()
    }

    // ── Private loaders ───────────────────────────────────────────────────────

    private fun loadHeroMovie() {
        viewModelScope.launch {
            try {
                repository.getHeroMovie().collect { hero ->
                    _homeState.update { state -> state.copy(heroMovie = hero) }
                }
            } catch (e: Exception) {
                _homeState.update { state -> state.copy(error = e.message) }
            }
        }
    }

    private fun loadContentRows() {
        viewModelScope.launch {
            _homeState.update { state -> state.copy(isLoading = true) }
            try {
                repository.getContentRows().collect { rows ->
                    _homeState.update { state ->
                        state.copy(
                            contentRows = rows,
                            isLoading = false,
                            error = null
                        )
                    }
                }
            } catch (e: Exception) {
                _homeState.update { state ->
                    state.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load content"
                    )
                }
            }
        }
    }

    private fun loadMyList() {
        viewModelScope.launch {
            try {
                repository.getMyList().collect { list ->
                    _myList.value = list
                }
            } catch (e: Exception) {
                // My List failure is non-critical — ignore silently
            }
        }
    }

    // ── Public actions ────────────────────────────────────────────────────────

    fun search(query: String) {
        _searchState.update { state -> state.copy(query = query, isSearching = true) }
        viewModelScope.launch {
            try {
                repository.searchMovies(query).collect { results ->
                    _searchState.update { state ->
                        state.copy(results = results, isSearching = false)
                    }
                }
            } catch (e: Exception) {
                _searchState.update { state ->
                    state.copy(isSearching = false, results = emptyList())
                }
            }
        }
    }

    fun selectMovie(movie: Movie) {
        _selectedMovie.value = movie
    }

    fun clearSelectedMovie() {
        _selectedMovie.value = null
    }

    /**
     * Adds movie to My List if not present, removes it if already there.
     * New items are inserted at the top (index 0).
     */
    fun toggleMyList(movie: Movie) {
        val current = _myList.value.toMutableList()
        val alreadyAdded = current.any { m -> m.id == movie.id }
        if (alreadyAdded) {
            current.removeAll { m -> m.id == movie.id }
        } else {
            current.add(0, movie)
        }
        _myList.value = current
    }

    /**
     * Returns true if the given movieId is in My List.
     * Safe to call from Composable — reads from StateFlow snapshot.
     */
    fun isInMyList(movieId: Int): Boolean {
        return _myList.value.any { m -> m.id == movieId }
    }

    /**
     * Retry loading home content after an error.
     */
    fun retryLoad() {
        _homeState.update { state -> state.copy(error = null) }
        loadHeroMovie()
        loadContentRows()
    }
}