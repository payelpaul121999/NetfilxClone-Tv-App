package com.droid.netfilxclone.ui.screen


import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.droid.netfilxclone.ui.theme.NetflixColors
import com.droid.netfilxclone.ui.theme.NetflixDimensions
import com.droid.netfilxclone.data.model.Movie
import com.droid.netfilxclone.ui.components.FocusableMovieCard

// ─────────────────────────────────────────────────────────────────────────────
// MyListScreen
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun MyListScreen(
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit,
    onRemoveFromList: ((Movie) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(NetflixColors.Black)
            .padding(horizontal = NetflixDimensions.paddingXxl, vertical = 24.dp)
    ) {

        // ── Header ────────────────────────────────────────────────────────────
        MyListHeader(count = movies.size)

        Spacer(Modifier.height(20.dp))

        // ── Content ───────────────────────────────────────────────────────────
        if (movies.isEmpty()) {
            EmptyMyList()
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(200.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                // Explicit 'movie' name — no 'it' shadowing in key lambda
                items(
                    items = movies,
                    key = { movie -> movie.id }
                ) { movie ->
                    MyListCard(
                        movie = movie,
                        onClick = { onMovieClick(movie) },
                        onRemove = onRemoveFromList?.let { remove ->
                            { remove(movie) }
                        }
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Header
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun MyListHeader(count: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "My List",
                color = NetflixColors.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = if (count == 0) "No titles saved"
                else "$count title${if (count == 1) "" else "s"}",
                color = NetflixColors.TextSecondary,
                fontSize = 14.sp
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// MyListCard — card with optional remove (×) button overlay
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun MyListCard(
    movie: Movie,
    onClick: () -> Unit,
    onRemove: (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Box(
        modifier = Modifier
            .width(NetflixDimensions.cardWidth)
            .height(NetflixDimensions.cardHeight)
    ) {
        // Base focusable card
        FocusableMovieCard(
            movie = movie,
            onClick = onClick
        )

        // Remove (×) button — top-right corner, only shown when focused
        if (isFocused && onRemove != null) {
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp)
                    .size(24.dp)
                    .clickable { onRemove() },
                color = Color(0xCC000000),
                shape = RoundedCornerShape(12.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Remove from My List",
                        tint = NetflixColors.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Empty state
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun EmptyMyList() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Bookmark icon — use Icons.Default.Bookmark (filled, exists in default set)
            Icon(
                imageVector = Icons.Default.Bookmark,
                contentDescription = null,
                tint = NetflixColors.LightGray,
                modifier = Modifier.size(72.dp)
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Your list is empty",
                color = NetflixColors.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Add shows and movies to watch them later.",
                color = NetflixColors.TextSecondary,
                fontSize = 15.sp
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Press + My List on any title to save it here.",
                color = NetflixColors.TextTertiary,
                fontSize = 13.sp
            )
        }
    }
}