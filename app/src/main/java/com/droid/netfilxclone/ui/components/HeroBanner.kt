package com.droid.netfilxclone.ui.components


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.droid.netfilxclone.ui.theme.NetflixColors
import com.droid.netfilxclone.ui.theme.NetflixDimensions
import com.droid.netfilxclone.data.model.Movie


@Composable
fun HeroBanner(
    movie: Movie,
    isInMyList: Boolean,
    onPlayClick: () -> Unit,
    onMoreInfoClick: () -> Unit,
    onMyListClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(NetflixDimensions.heroHeight)
    ) {
        // Background Image
        AsyncImage(
            model = movie.backdropUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Dark gradients (left + bottom)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        0f to Color(0xCC000000),
                        0.5f to Color(0x66000000),
                        1f to Color.Transparent
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to Color.Transparent,
                        0.6f to Color.Transparent,
                        1f to Color(0xFF000000)
                    )
                )
        )

        // Content
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = NetflixDimensions.paddingXxl, bottom = 40.dp)
                .fillMaxWidth(0.5f)
        ) {
            // Netflix Original badge
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(width = 22.dp, height = 22.dp)
                        .background(NetflixColors.Red, RoundedCornerShape(2.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("N", color = NetflixColors.White, fontSize = 14.sp, fontWeight = FontWeight.Black)
                }
                Spacer(Modifier.width(6.dp))
                Text(
                    "ORIGINAL SERIES",
                    color = NetflixColors.TextSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }

            Spacer(Modifier.height(12.dp))

            // Title
            Text(
                text = movie.title,
                color = NetflixColors.White,
                fontSize = 52.sp,
                fontWeight = FontWeight.Black,
                lineHeight = 54.sp
            )

            Spacer(Modifier.height(12.dp))

            // Meta info row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Rating
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = NetflixColors.Gold,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(3.dp))
                    Text(
                        "${movie.rating}",
                        color = NetflixColors.TextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Text("•", color = NetflixColors.TextSecondary)
                Text(
                    "${movie.year}",
                    color = NetflixColors.TextSecondary,
                    fontSize = 13.sp
                )
                Text("•", color = NetflixColors.TextSecondary)
                Text(
                    movie.duration,
                    color = NetflixColors.TextSecondary,
                    fontSize = 13.sp
                )
                MaturityBadge(movie.maturityRating)
            }

            Spacer(Modifier.height(12.dp))

            // Genres
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                movie.genre.take(3).forEach { GenreChip(it) }
            }

            Spacer(Modifier.height(16.dp))

            // Description
            Text(
                text = movie.description,
                color = NetflixColors.TextSecondary,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(24.dp))

            // Action Buttons
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                NetflixButton(
                    text = "▶  Play",
                    onClick = onPlayClick,
                    modifier = Modifier.width(140.dp)
                )
                NetflixButton(
                    text = "ⓘ  More Info",
                    onClick = onMoreInfoClick,
                    isSecondary = true,
                    modifier = Modifier.width(160.dp)
                )
                // My List button
                val interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                Surface(
                    modifier = Modifier
                        .size(44.dp)
                        .clickable(interactionSource = interactionSource, indication = null) { onMyListClick() },
                    color = Color(0x66808080),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            if (isInMyList) Icons.Default.Check else Icons.Default.Add,
                            contentDescription = if (isInMyList) "Remove from My List" else "Add to My List",
                            tint = NetflixColors.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }

        // Top-10 rank indicator (right side)
        if (movie.isTopTen) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "#1",
                    color = NetflixColors.White,
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.offset(y = 8.dp)
                )
                Text(
                    "IN INDIA TODAY",
                    color = NetflixColors.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
            }
        }
    }
}