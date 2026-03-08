package com.droid.netfilxclone.ui.screen


import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.*
import com.droid.netfilxclone.ui.theme.NetflixColors
import com.droid.netfilxclone.ui.theme.NetflixDimensions
import com.droid.netfilxclone.data.model.Movie
import com.droid.netfilxclone.ui.components.FocusableMovieCard


// ─────────────────────────────────────────────────────────────────────────────
// Category data
// ─────────────────────────────────────────────────────────────────────────────

private data class SearchCategory(val name: String, val color: Color)

private val searchCategories = listOf(
    SearchCategory("Action & Adventure", Color(0xFFE50914)),
    SearchCategory("Comedy",             Color(0xFF2563EB)),
    SearchCategory("Drama",              Color(0xFF7C3AED)),
    SearchCategory("Horror",             Color(0xFF1D4ED8)),
    SearchCategory("Sci-Fi & Fantasy",   Color(0xFF0891B2)),
    SearchCategory("Crime",              Color(0xFF15803D)),
    SearchCategory("Romance",            Color(0xFFDB2777)),
    SearchCategory("Thriller",           Color(0xFFD97706)),
    SearchCategory("Documentary",        Color(0xFF9333EA)),
    SearchCategory("Anime",              Color(0xFFEA580C)),
    SearchCategory("K-Drama",            Color(0xFF0D9488)),
    SearchCategory("Reality TV",         Color(0xFFB45309))
)

// ─────────────────────────────────────────────────────────────────────────────
// SearchScreen
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun SearchScreen(
    query: String,
    results: List<Movie>,
    isSearching: Boolean,
    onQueryChange: (String) -> Unit,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NetflixColors.Black)
            .padding(horizontal = NetflixDimensions.paddingXxl, vertical = 24.dp)
    ) {
        Text(
            text = "Search",
            color = NetflixColors.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))

        // Search input — named NetflixSearchBar to avoid clash with M3 SearchBar
        NetflixSearchBar(
            query = query,
            onQueryChange = onQueryChange,
            focusRequester = focusRequester
        )

        Spacer(Modifier.height(24.dp))

        when {
            query.isBlank() -> {
                BrowseByCategory()
            }
            isSearching -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        color = NetflixColors.Red,
                        strokeWidth = 3.dp,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            results.isEmpty() -> {
                EmptySearchResult(query = query)
            }
            else -> {
                Text(
                    text = "${results.size} title${if (results.size == 1) "" else "s"} found",
                    color = NetflixColors.TextSecondary,
                    fontSize = 14.sp
                )
                Spacer(Modifier.height(12.dp))
                SearchResultsGrid(
                    movies = results,
                    onMovieClick = onMovieClick
                )
            }
        }
    }

    // Auto-focus the search field when screen opens
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// NetflixSearchBar  (NOT named SearchBar — avoids M3 SearchBar name clash)
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun NetflixSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    focusRequester: FocusRequester
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        color = Color(0xFF2F2F2F),
        shape = RoundedCornerShape(4.dp),
        border = if (isFocused) BorderStroke(2.dp, NetflixColors.White) else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = if (isFocused) NetflixColors.White else NetflixColors.LightGray,
                modifier = Modifier.size(22.dp)
            )

            Spacer(Modifier.width(12.dp))

            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                textStyle = TextStyle(
                    color = NetflixColors.White,
                    fontSize = 16.sp
                ),
                singleLine = true,
                cursorBrush = SolidColor(NetflixColors.Red),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                interactionSource = interactionSource,
                decorationBox = { innerTextField ->
                    if (query.isEmpty()) {
                        Text(
                            text = "Search titles, genres, people...",
                            color = NetflixColors.LightGray,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                },
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester)
            )

            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear search",
                        tint = NetflixColors.LightGray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Browse by Category grid
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun BrowseByCategory() {
    Text(
        text = "Browse by Category",
        color = NetflixColors.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(16.dp))
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = searchCategories,
            key = { category -> category.name }
        ) { category ->
            CategoryCard(category = category)
        }
    }
}

@Composable
private fun CategoryCard(category: SearchCategory) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Surface(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .then(
                if (isFocused) Modifier.border(
                    width = 2.dp,
                    color = NetflixColors.White,
                    shape = RoundedCornerShape(6.dp)
                ) else Modifier
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {}
            ),
        color = category.color,
        shape = RoundedCornerShape(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = category.name,
                color = NetflixColors.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Search results grid
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun SearchResultsGrid(
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        // Explicit 'movie' name — no 'it' shadowing
        items(
            items = movies,
            key = { movie -> movie.id }
        ) { movie ->
            FocusableMovieCard(
                movie = movie,
                onClick = { onMovieClick(movie) }
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Empty state
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun EmptySearchResult(query: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                // SearchOff doesn't exist in default set — use SentimentDissatisfied
                imageVector = Icons.Default.SentimentDissatisfied,
                contentDescription = null,
                tint = NetflixColors.LightGray,
                modifier = Modifier.size(72.dp)
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = "No results for \"$query\"",
                color = NetflixColors.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Try different keywords or check for typos.",
                color = NetflixColors.TextSecondary,
                fontSize = 14.sp
            )
        }
    }
}