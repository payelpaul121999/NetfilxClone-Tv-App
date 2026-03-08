package com.droid.netfilxclone.ui.components


import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.droid.netfilxclone.ui.theme.NetflixColors
import com.droid.netfilxclone.ui.theme.NetflixDimensions
import com.droid.netfilxclone.data.model.Movie


// ─────────────────────────────────────────────────────────────────────────────
// Focusable Card — scales up and shows border on D-Pad focus
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun FocusableMovieCard(
    movie: Movie,
    modifier: Modifier = Modifier,
    onFocus: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.08f else 1f,
        animationSpec = tween(150),
        label = "card_scale"
    )

    LaunchedEffect(isFocused) {
        if (isFocused) onFocus()
    }

    Box(
        modifier = modifier
            .width(NetflixDimensions.cardWidth)
            .height(NetflixDimensions.cardHeight)
            .scale(scale)
            .clip(RoundedCornerShape(NetflixDimensions.cornerRadius))
            .then(
                if (isFocused) Modifier.border(
                    NetflixDimensions.focusBorderWidth,
                    NetflixColors.FocusedBorder,
                    RoundedCornerShape(NetflixDimensions.cornerRadius)
                ) else Modifier
            )
            .clickable(interactionSource = interactionSource, indication = null) { onClick() }
    ) {
        AsyncImage(
            model = movie.thumbnailUrl,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay at bottom
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to Color.Transparent,
                        0.6f to Color.Transparent,
                        1f to Color(0xCC000000)
                    )
                )
        )

        // Top-10 badge
        if (movie.isTopTen) {
            Surface(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(6.dp),
                color = NetflixColors.Red,
                shape = RoundedCornerShape(2.dp)
            ) {
                Text(
                    "TOP 10",
                    color = NetflixColors.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }
        }

        // NEW badge
        if (movie.isNew) {
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp),
                color = Color(0xFF5AA02C),
                shape = RoundedCornerShape(2.dp)
            ) {
                Text(
                    "NEW",
                    color = NetflixColors.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }
        }

        // Title at bottom
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
        ) {
            Text(
                text = movie.title,
                color = NetflixColors.TextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            AnimatedVisibility(visible = isFocused) {
                Text(
                    text = movie.genre.take(2).joinToString(" • "),
                    color = NetflixColors.TextSecondary,
                    fontSize = 10.sp,
                    maxLines = 1
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Portrait Card (for My List, etc.)
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun FocusablePortraitCard(
    movie: Movie,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.08f else 1f,
        animationSpec = tween(150),
        label = "portrait_scale"
    )

    Box(
        modifier = modifier
            .width(NetflixDimensions.thumbnailWidth)
            .height(NetflixDimensions.thumbnailHeight)
            .scale(scale)
            .clip(RoundedCornerShape(NetflixDimensions.cornerRadius))
            .then(
                if (isFocused) Modifier.border(
                    NetflixDimensions.focusBorderWidth,
                    NetflixColors.FocusedBorder,
                    RoundedCornerShape(NetflixDimensions.cornerRadius)
                ) else Modifier
            )
            .clickable(interactionSource = interactionSource, indication = null) { onClick() }
    ) {
        AsyncImage(
            model = movie.thumbnailUrl,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to Color.Transparent,
                        0.7f to Color.Transparent,
                        1f to Color(0xCC000000)
                    )
                )
        )
        Text(
            text = movie.title,
            color = NetflixColors.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Netflix-style primary button
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun NetflixButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    isSecondary: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val bgColor = when {
        isSecondary && isFocused -> Color(0xFFFFFFFF)
        isSecondary -> Color(0x66808080)
        isFocused -> NetflixColors.RedDark
        else -> NetflixColors.Red
    }
    val textColor = if (isSecondary && !isFocused) NetflixColors.White else NetflixColors.Black.takeIf { isSecondary } ?: NetflixColors.White

    Surface(
        modifier = modifier
            .height(44.dp)
            .then(
                if (isFocused) Modifier.border(2.dp, NetflixColors.White, RoundedCornerShape(4.dp))
                else Modifier
            )
            .clickable(interactionSource = interactionSource, indication = null) { onClick() },
        color = bgColor,
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            icon?.invoke()
            if (icon != null) Spacer(Modifier.width(8.dp))
            Text(
                text = text,
                color = if (isSecondary) NetflixColors.White else NetflixColors.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Maturity Rating Badge
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun MaturityBadge(rating: String) {
    Surface(
        color = Color.Transparent,
        shape = RoundedCornerShape(2.dp),
        border = BorderStroke(1.dp, NetflixColors.LightGray)
    ) {
        Text(
            text = rating,
            color = NetflixColors.TextSecondary,
            fontSize = 11.sp,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Loading shimmer placeholder
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun ShimmerCard(modifier: Modifier = Modifier) {
    val shimmerTranslate = rememberInfiniteTransition(label = "shimmer")
    val translateX by shimmerTranslate.animateFloat(
        initialValue = -300f,
        targetValue = 300f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_x"
    )

    Box(
        modifier = modifier
            .width(NetflixDimensions.cardWidth)
            .height(NetflixDimensions.cardHeight)
            .clip(RoundedCornerShape(NetflixDimensions.cornerRadius))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF2F2F2F),
                        Color(0xFF3F3F3F),
                        Color(0xFF2F2F2F)
                    ),
                    startX = translateX,
                    endX = translateX + 600f
                )
            )
    )
}

// ─────────────────────────────────────────────────────────────────────────────
// Genre Chip
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun GenreChip(genre: String) {
    Surface(
        color = Color(0x33FFFFFF),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = genre,
            color = NetflixColors.TextPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Profile Avatar
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun ProfileAvatar(
    name: String,
    color: Long,
    size: Dp = 80.dp,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.1f else 1f,
        label = "avatar_scale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .scale(scale)
            .clickable(interactionSource = interactionSource, indication = null) { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(color))
                .then(
                    if (isFocused) Modifier.border(3.dp, NetflixColors.White, RoundedCornerShape(8.dp))
                    else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.first().toString(),
                color = NetflixColors.White,
                fontSize = (size.value * 0.4f).sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = name,
            color = if (isFocused) NetflixColors.White else NetflixColors.TextSecondary,
            fontSize = 14.sp,
            fontWeight = if (isFocused) FontWeight.Bold else FontWeight.Normal
        )
    }
}