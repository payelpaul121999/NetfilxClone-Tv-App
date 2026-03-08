package com.droid.netfilxclone.ui.screen


import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.droid.netfilxclone.ui.theme.NetflixColors
import com.droid.netfilxclone.data.model.UserProfile


@Composable
fun ProfileSelectionScreen(
    profiles: List<UserProfile>,
    onProfileSelected: (UserProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(NetflixColors.Black),
        contentAlignment = Alignment.Center
    ) {
        // Subtle background gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0x22E50914), Color.Transparent),
                        radius = 800f
                    )
                )
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // Netflix Logo
            Text(
                text = "NETFLIX",
                color = NetflixColors.Red,
                fontSize = 42.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 6.sp
            )

            Spacer(Modifier.height(52.dp))

            Text(
                text = "Who's watching?",
                color = NetflixColors.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = 1.sp
            )

            Spacer(Modifier.height(48.dp))

            // Profile avatars row
            Row(
                horizontalArrangement = Arrangement.spacedBy(36.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                profiles.forEach { profile ->
                    ProfileCard(
                        profile = profile,
                        onClick = { onProfileSelected(profile) }
                    )
                }

                // Add Profile
                AddProfileCard(onClick = {})
            }

            Spacer(Modifier.height(52.dp))

            // Manage Profiles button
            ManageProfilesButton(onClick = {})
        }
    }
}

@Composable
private fun ProfileCard(
    profile: UserProfile,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.12f else 1f,
        animationSpec = tween(150),
        label = "profile_scale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .scale(scale)
            .clickable(interactionSource = interactionSource, indication = null) { onClick() }
    ) {
        // Avatar box
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(profile.avatarColor))
                .then(
                    if (isFocused) Modifier.border(
                        width = 3.dp,
                        color = NetflixColors.White,
                        shape = RoundedCornerShape(8.dp)
                    ) else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            if (profile.isKidsProfile) {
                Icon(
                    imageVector = Icons.Default.ChildCare,
                    contentDescription = null,
                    tint = NetflixColors.White,
                    modifier = Modifier.size(52.dp)
                )
            } else {
                Text(
                    text = profile.name.first().uppercaseChar().toString(),
                    color = NetflixColors.White,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = profile.name,
            color = if (isFocused) NetflixColors.White else NetflixColors.TextSecondary,
            fontSize = 16.sp,
            fontWeight = if (isFocused) FontWeight.Bold else FontWeight.Normal
        )

        // Kids badge
        if (profile.isKidsProfile) {
            Spacer(Modifier.height(4.dp))
            Surface(
                color = Color(0xFF0F79AF),
                shape = RoundedCornerShape(3.dp)
            ) {
                Text(
                    text = "KIDS",
                    color = NetflixColors.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun AddProfileCard(onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.12f else 1f,
        animationSpec = tween(150),
        label = "add_scale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .scale(scale)
            .clickable(interactionSource = interactionSource, indication = null) { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF2F2F2F))
                .then(
                    if (isFocused) Modifier.border(
                        width = 3.dp,
                        color = NetflixColors.White,
                        shape = RoundedCornerShape(8.dp)
                    ) else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Profile",
                tint = if (isFocused) NetflixColors.White else NetflixColors.LightGray,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = "Add Profile",
            color = if (isFocused) NetflixColors.White else NetflixColors.TextSecondary,
            fontSize = 16.sp,
            fontWeight = if (isFocused) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun ManageProfilesButton(onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Surface(
        modifier = Modifier
            .height(44.dp)
            .widthIn(min = 180.dp)
            .then(
                if (isFocused) Modifier.border(
                    width = 2.dp,
                    color = NetflixColors.White,
                    shape = RoundedCornerShape(4.dp)
                ) else Modifier
            )
            .clickable(interactionSource = interactionSource, indication = null) { onClick() },
        color = if (isFocused) Color(0xFF333333) else Color.Transparent,
        shape = RoundedCornerShape(4.dp),
        border = if (!isFocused) BorderStroke(1.dp, NetflixColors.LightGray) else null
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = NetflixColors.White,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Manage Profiles",
                color = NetflixColors.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}