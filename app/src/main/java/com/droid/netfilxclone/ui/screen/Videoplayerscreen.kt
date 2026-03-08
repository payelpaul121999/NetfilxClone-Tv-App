/*
package com.droid.githubuserdetails

import com.droid.githubuserdetails.data.Movie

package com.netflix.tvclone.ui.screens

import android.app.Activity
import android.view.WindowManager
import androidx.annotation.OptIn
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.delay

// ─────────────────────────────────────────────────────────────────────────────
// VideoPlayerScreen
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerScreen(
    movie: Movie,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // ── Keep screen on while playing ──────────────────────────────────────────
    DisposableEffect(Unit) {
        val activity = context as? Activity
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        onDispose {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    // ── ExoPlayer setup ───────────────────────────────────────────────────────
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = if (movie.videoUrl.isNotBlank()) {
                MediaItem.fromUri(movie.videoUrl)
            } else {
                // Demo stream — Big Buck Bunny (replace with real URL)
                MediaItem.fromUri(
                    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                )
            }
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            repeatMode = Player.REPEAT_MODE_OFF
        }
    }

    // ── Player state ──────────────────────────────────────────────────────────
    var isPlaying       by remember { mutableStateOf(true) }
    var showControls    by remember { mutableStateOf(true) }
    var currentPosition by remember { mutableLongStateOf(0L) }
    var totalDuration   by remember { mutableLongStateOf(0L) }
    var bufferedPercent by remember { mutableIntStateOf(0) }
    var isBuffering     by remember { mutableStateOf(true) }
    var isMuted         by remember { mutableStateOf(false) }

    // Auto-hide controls after 4 seconds
    LaunchedEffect(showControls) {
        if (showControls) {
            delay(4000)
            showControls = false
        }
    }

    // Poll player state every 500ms
    LaunchedEffect(exoPlayer) {
        while (true) {
            currentPosition = exoPlayer.currentPosition.coerceAtLeast(0L)
            totalDuration   = exoPlayer.duration.coerceAtLeast(0L)
            bufferedPercent = exoPlayer.bufferedPercentage
            isPlaying       = exoPlayer.isPlaying
            isBuffering     = exoPlayer.playbackState == Player.STATE_BUFFERING
            delay(500)
        }
    }

    // Listener for playback state changes
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) { isPlaying = playing }
            override fun onPlaybackStateChanged(state: Int) {
                isBuffering = state == Player.STATE_BUFFERING
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    // ── UI ────────────────────────────────────────────────────────────────────
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication        = null
            ) { showControls = !showControls }
    ) {

        // ── ExoPlayer Surface ─────────────────────────────────────────────────
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player      = exoPlayer
                    useController = false   // we draw our own controls
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // ── Buffering spinner ─────────────────────────────────────────────────
        if (isBuffering) {
            CircularProgressIndicator(
                color       = NetflixColors.Red,
                strokeWidth = 3.dp,
                modifier    = Modifier
                    .size(52.dp)
                    .align(Alignment.Center)
            )
        }

        // ── Controls overlay ──────────────────────────────────────────────────
        AnimatedVisibility(
            visible = showControls,
            enter   = fadeIn(),
            exit    = fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            PlayerControlsOverlay(
                movie           = movie,
                isPlaying       = isPlaying,
                isMuted         = isMuted,
                currentPosition = currentPosition,
                totalDuration   = totalDuration,
                bufferedPercent = bufferedPercent,
                onPlayPause     = {
                    if (exoPlayer.isPlaying) exoPlayer.pause() else exoPlayer.play()
                    showControls = true
                },
                onSeekForward   = {
                    exoPlayer.seekTo((exoPlayer.currentPosition + 10_000).coerceAtMost(totalDuration))
                    showControls = true
                },
                onSeekBackward  = {
                    exoPlayer.seekTo((exoPlayer.currentPosition - 10_000).coerceAtLeast(0))
                    showControls = true
                },
                onSeekTo        = { position ->
                    exoPlayer.seekTo(position)
                    showControls = true
                },
                onMuteToggle    = {
                    isMuted = !isMuted
                    exoPlayer.volume = if (isMuted) 0f else 1f
                },
                onBackClick     = onBackClick
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PlayerControlsOverlay
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun PlayerControlsOverlay(
    movie: Movie,
    isPlaying: Boolean,
    isMuted: Boolean,
    currentPosition: Long,
    totalDuration: Long,
    bufferedPercent: Int,
    onPlayPause: () -> Unit,
    onSeekForward: () -> Unit,
    onSeekBackward: () -> Unit,
    onSeekTo: (Long) -> Unit,
    onMuteToggle: () -> Unit,
    onBackClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        // Top gradient
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xCC000000), Color.Transparent)
                    )
                )
        )

        // Bottom gradient
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color(0xCC000000))
                    )
                )
        )

        // ── Top bar — back + title + mute ─────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(horizontal = 32.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button
            PlayerIconButton(
                icon               = Icons.Default.ArrowBack,
                contentDescription = "Back",
                onClick            = onBackClick
            )

            Spacer(Modifier.width(20.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text       = movie.title,
                    color      = NetflixColors.White,
                    fontSize   = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines   = 1,
                    overflow   = TextOverflow.Ellipsis
                )
                Text(
                    text     = "${movie.year}  •  ${movie.duration}  •  ${movie.maturityRating}",
                    color    = NetflixColors.TextSecondary,
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.width(16.dp))

            // Mute toggle
            PlayerIconButton(
                icon               = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                contentDescription = if (isMuted) "Unmute" else "Mute",
                onClick            = onMuteToggle
            )
        }

        // ── Centre playback controls ──────────────────────────────────────────
        Row(
            modifier              = Modifier.align(Alignment.Center),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            // ← 10s rewind
            PlayerIconButton(
                icon               = Icons.Default.Replay10,
                contentDescription = "Rewind 10s",
                size               = 40.dp,
                onClick            = onSeekBackward
            )

            // Play / Pause (large)
            PlayPauseButton(
                isPlaying = isPlaying,
                onClick   = onPlayPause
            )

            // → 10s forward
            PlayerIconButton(
                icon               = Icons.Default.Forward10,
                contentDescription = "Forward 10s",
                size               = 40.dp,
                onClick            = onSeekForward
            )
        }

        // ── Bottom bar — progress + time ──────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 32.dp, vertical = 24.dp)
        ) {
            // Time labels
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text     = formatTime(currentPosition),
                    color    = NetflixColors.White,
                    fontSize = 13.sp
                )
                Text(
                    text     = formatTime(totalDuration),
                    color    = NetflixColors.TextSecondary,
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.height(6.dp))

            // Progress bar
            VideoProgressBar(
                currentPosition = currentPosition,
                totalDuration   = totalDuration,
                bufferedPercent = bufferedPercent,
                onSeekTo        = onSeekTo
            )

            Spacer(Modifier.height(12.dp))

            // Bottom row — episodes + subtitles + speed
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                BottomControlChip(label = "Episodes",  icon = Icons.Default.List)
                Spacer(Modifier.width(12.dp))
                BottomControlChip(label = "Audio & Subtitles", icon = Icons.Default.Subtitles)
                Spacer(Modifier.width(12.dp))
                BottomControlChip(label = "Speed",     icon = Icons.Default.Speed)
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// VideoProgressBar — seekable scrubber
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun VideoProgressBar(
    currentPosition: Long,
    totalDuration: Long,
    bufferedPercent: Int,
    onSeekTo: (Long) -> Unit
) {
    val progress = if (totalDuration > 0) currentPosition.toFloat() / totalDuration else 0f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .clip(RoundedCornerShape(2.dp))
    ) {
        // Track background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x66FFFFFF))
        )
        // Buffered
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(bufferedPercent / 100f)
                .background(Color(0x99FFFFFF))
        )
        // Played
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress)
                .background(NetflixColors.Red)
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PlayPauseButton — large centred button
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun PlayPauseButton(isPlaying: Boolean, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Surface(
        modifier = Modifier
            .size(72.dp)
            .then(
                if (isFocused) Modifier.border(3.dp, NetflixColors.White, CircleShape)
                else Modifier
            )
            .clickable(
                interactionSource = interactionSource,
                indication        = null,
                onClick           = onClick
            ),
        color  = Color(0xBBFFFFFF),
        shape  = CircleShape
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector        = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = if (isPlaying) "Pause" else "Play",
                tint               = Color.Black,
                modifier           = Modifier.size(40.dp)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PlayerIconButton — generic icon button with TV focus
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun PlayerIconButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    size: Dp = 28.dp
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Box(
        modifier = Modifier
            .size(size + 16.dp)
            .clip(CircleShape)
            .background(if (isFocused) Color(0x33FFFFFF) else Color.Transparent)
            .clickable(
                interactionSource = interactionSource,
                indication        = null,
                onClick           = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector        = icon,
            contentDescription = contentDescription,
            tint               = NetflixColors.White,
            modifier           = Modifier.size(size)
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// BottomControlChip — Episodes / Subtitles / Speed chips
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun BottomControlChip(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Surface(
        modifier = Modifier
            .height(32.dp)
            .then(
                if (isFocused) Modifier.border(1.dp, NetflixColors.White, RoundedCornerShape(16.dp))
                else Modifier
            )
            .clickable(
                interactionSource = interactionSource,
                indication        = null,
                onClick           = {}
            ),
        color = Color(0x44000000),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier          = Modifier.padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector        = icon,
                contentDescription = null,
                tint               = NetflixColors.White,
                modifier           = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text     = label,
                color    = NetflixColors.White,
                fontSize = 12.sp
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Helpers
// ─────────────────────────────────────────────────────────────────────────────

private fun formatTime(ms: Long): String {
    if (ms <= 0L) return "0:00"
    val totalSeconds = ms / 1000
    val hours        = totalSeconds / 3600
    val minutes      = (totalSeconds % 3600) / 60
    val seconds      = totalSeconds % 60
    return if (hours > 0) {
        "%d:%02d:%02d".format(hours, minutes, seconds)
    } else {
        "%d:%02d".format(minutes, seconds)
    }
}*/
