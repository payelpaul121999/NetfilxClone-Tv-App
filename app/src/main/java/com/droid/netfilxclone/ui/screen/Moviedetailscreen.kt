package com.droid.netfilxclone.ui.screen

import com.droid.netfilxclone.data.model.Movie


import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.droid.netfilxclone.ui.theme.NetflixColors
import com.droid.netfilxclone.ui.theme.NetflixDimensions
import com.droid.netfilxclone.ui.components.FocusableMovieCard
import com.droid.netfilxclone.ui.components.GenreChip
import com.droid.netfilxclone.ui.components.MaturityBadge
import com.droid.netfilxclone.ui.components.NetflixButton


@Composable
fun MovieDetailScreen(
    movie: Movie,
    isInMyList: Boolean,
    similarMovies: List<Movie>,
    onPlayClick: () -> Unit,
    onBackClick: () -> Unit,
    onMyListToggle: () -> Unit,
    onSimilarMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(NetflixColors.Black)
    ) {
        Column(modifier = Modifier.verticalScroll(scrollState)) {

            // ── Backdrop ──────────────────────────────────────────────────────
            BackdropSection(
                movie = movie,
                onPlayClick = onPlayClick,
                onBackClick = onBackClick
            )

            // ── Info ──────────────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = NetflixDimensions.paddingXxl)
            ) {
                Spacer(Modifier.height(4.dp))

                Text(
                    text = movie.title,
                    color = NetflixColors.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(Modifier.height(10.dp))
                MetaRow(movie = movie)

                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    movie.genre.forEach { genre -> GenreChip(genre) }
                }

                Spacer(Modifier.height(20.dp))
                ActionButtonsRow(
                    isInMyList = isInMyList,
                    onPlayClick = onPlayClick,
                    onMyListToggle = onMyListToggle
                )

                Spacer(Modifier.height(24.dp))
                Text(
                    text = movie.description,
                    color = NetflixColors.TextSecondary,
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )

                Spacer(Modifier.height(16.dp))
                if (movie.cast.isNotEmpty()) {
                    Row {
                        Text("Cast: ", color = NetflixColors.TextTertiary, fontSize = 13.sp)
                        Text(
                            text = movie.cast.joinToString(", "),
                            color = NetflixColors.TextSecondary,
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(Modifier.height(32.dp))
                Divider(color = Color(0x33FFFFFF))
                Spacer(Modifier.height(24.dp))

                Text(
                    text = "More Like This",
                    color = NetflixColors.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(16.dp))
            }

            // ── Similar movies ─────────────────────────────────────────────────
            if (similarMovies.isEmpty()) {
                Text(
                    text = "No similar titles found.",
                    color = NetflixColors.TextSecondary,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(
                        horizontal = NetflixDimensions.paddingXxl,
                        vertical = 8.dp
                    )
                )
            } else {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = NetflixDimensions.paddingXxl),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(bottom = 48.dp)
                ) {
                    // Explicit 'similarMovie' name — no 'it' shadowing
                    items(
                        items = similarMovies,
                        key = { similarMovie -> similarMovie.id }
                    ) { similarMovie ->
                        FocusableMovieCard(
                            movie = similarMovie,
                            onClick = { onSimilarMovieClick(similarMovie) }
                        )
                    }
                }
            }
        }
    }
}

// ── Sub-composables ──────────────────────────────────────────────────────────

@Composable
private fun BackdropSection(
    movie: Movie,
    onPlayClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(420.dp)
    ) {
        AsyncImage(
            model = movie.backdropUrl,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to Color(0x22000000),
                        0.55f to Color(0x66000000),
                        1f to Color(0xFF000000)
                    )
                )
        )
        // Play button
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .size(72.dp)
                .clickable { onPlayClick() },
            color = Color(0xBBFFFFFF),
            shape = CircleShape
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = NetflixColors.Black,
                    modifier = Modifier.size(44.dp)
                )
            }
        }
        // Back button
        Surface(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(20.dp)
                .size(40.dp)
                .clickable { onBackClick() },
            color = Color(0x88000000),
            shape = CircleShape
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = NetflixColors.White,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

@Composable
private fun MetaRow(movie: Movie) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = NetflixColors.Gold,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(3.dp))
            Text(
                text = "${movie.rating}/10",
                color = NetflixColors.TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Text("•", color = NetflixColors.TextSecondary, fontSize = 14.sp)
        Text(text = "${movie.year}", color = NetflixColors.TextSecondary, fontSize = 14.sp)
        Text("•", color = NetflixColors.TextSecondary, fontSize = 14.sp)
        Text(text = movie.duration, color = NetflixColors.TextSecondary, fontSize = 14.sp)
        MaturityBadge(rating = movie.maturityRating)
    }
}

@Composable
private fun ActionButtonsRow(
    isInMyList: Boolean,
    onPlayClick: () -> Unit,
    onMyListToggle: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        NetflixButton(
            text = "▶  Play",
            onClick = onPlayClick,
            modifier = Modifier.width(160.dp)
        )
        NetflixButton(
            text = if (isInMyList) "✓  My List" else "+  My List",
            onClick = onMyListToggle,
            isSecondary = true,
            modifier = Modifier.width(150.dp)
        )
        NetflixButton(
            text = "⬇  Download",
            onClick = {},
            isSecondary = true,
            modifier = Modifier.width(160.dp)
        )
        val shareInteraction = remember { MutableInteractionSource() }
        Surface(
            modifier = Modifier
                .size(44.dp)
                .clickable(
                    interactionSource = shareInteraction,
                    indication = null,
                    onClick = {}
                ),
            color = Color(0x66808080),
            shape = RoundedCornerShape(4.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    tint = NetflixColors.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}