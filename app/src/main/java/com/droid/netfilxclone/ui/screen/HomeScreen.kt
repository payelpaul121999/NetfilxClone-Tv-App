package com.droid.netfilxclone.ui.screen


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.droid.netfilxclone.ui.theme.NetflixColors
import com.droid.netfilxclone.ui.theme.NetflixDimensions
import com.droid.netfilxclone.data.model.ContentRow
import com.droid.netfilxclone.data.model.Movie
import com.droid.netfilxclone.ui.components.FocusableMovieCard
import com.droid.netfilxclone.ui.components.HeroBanner


@Composable
fun HomeScreen(
    heroMovie: Movie?,
    contentRows: List<ContentRow>,
    isLoading: Boolean,
    isInMyList: (Int) -> Boolean,
    onMovieClick: (Movie) -> Unit,
    onMyListToggle: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()

    if (isLoading) {
        LoadingScreen()
        return
    }

    LazyColumn(
        state = scrollState,
        modifier = modifier
            .fillMaxSize()
            .background(NetflixColors.Black),
        contentPadding = PaddingValues(bottom = 48.dp)
    ) {
        // Hero Banner
        heroMovie?.let { movie ->
            item {
                HeroBanner(
                    movie = movie,
                    isInMyList = isInMyList(movie.id),
                    onPlayClick = { onMovieClick(movie) },
                    onMoreInfoClick = { onMovieClick(movie) },
                    onMyListClick = { onMyListToggle(movie) }
                )
            }
        }

        // Content rows
        items(contentRows) { row ->
            ContentRowSection(
                row = row,
                onMovieClick = onMovieClick
            )
        }
    }
}

@Composable
fun ContentRowSection(
    row: ContentRow,
    onMovieClick: (Movie) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Row Title
        Text(
            text = row.title,
            color = NetflixColors.TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                start = NetflixDimensions.paddingXxl,
                bottom = 12.dp
            )
        )

        // Horizontal scroll of cards
        LazyRow(
            contentPadding = PaddingValues(horizontal = NetflixDimensions.paddingXxl),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(row.movies, key = { it.id }) { movie ->
                FocusableMovieCard(
                    movie = movie,
                    onClick = { onMovieClick(movie) }
                )
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NetflixColors.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(NetflixColors.Red, androidx.compose.foundation.shape.RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "N",
                    color = NetflixColors.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black
                )
            }
            Spacer(Modifier.height(24.dp))
            /*CircularProgressIndicator(
                color = NetflixColors.Red,
                strokeWidth = 3.dp,
                modifier = Modifier.size(36.dp)
            )*/
        }
    }
}