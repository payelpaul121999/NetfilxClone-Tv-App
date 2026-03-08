package com.droid.netfilxclone.ui.components


import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.droid.netfilxclone.ui.theme.NetflixColors

// ─────────────────────────────────────────────────────────────────────────────
// NavItem model
// ─────────────────────────────────────────────────────────────────────────────

data class NavItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector = icon
)

val navItems = listOf(
    NavItem(route = "home",     label = "Home",     icon = Icons.Default.Home),
    NavItem(route = "tv_shows", label = "TV Shows", icon = Icons.Default.Tv),
    NavItem(route = "movies",   label = "Movies",   icon = Icons.Default.Movie),
    NavItem(route = "my_list",  label = "My List",  icon = Icons.Default.Bookmark),
    NavItem(route = "search",   label = "Search",   icon = Icons.Default.Search)
)

private val settingsItem = NavItem(
    route = "settings",
    label = "Settings",
    icon  = Icons.Default.Settings
)

// ─────────────────────────────────────────────────────────────────────────────
// NetflixSideNav
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun NetflixSideNav(
    currentRoute: String,
    isExpanded: Boolean,
    onRouteSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val width by animateDpAsState(
        targetValue = if (isExpanded) 220.dp else 72.dp,
        animationSpec = tween(durationMillis = 200),
        label = "nav_width"
    )

    Surface(
        modifier = modifier
            .width(width)
            .fillMaxHeight(),
        color = if (isExpanded) Color(0xE6000000) else Color.Black  /*Color.Black*/
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 32.dp)
        ) {

            // ── Netflix logo ──────────────────────────────────────────────────
            NavLogo(isExpanded = isExpanded)

            Spacer(Modifier.height(8.dp))

            // ── Main nav items ────────────────────────────────────────────────
            navItems.forEach { item ->
                NavItemRow(
                    item       = item,
                    isSelected = currentRoute == item.route,
                    isExpanded = isExpanded,
                    onClick    = { onRouteSelected(item.route) }
                )
            }

            Spacer(Modifier.weight(1f))

            // ── Settings pinned at bottom ─────────────────────────────────────
            NavItemRow(
                item       = settingsItem,
                isSelected = currentRoute == settingsItem.route,
                isExpanded = isExpanded,
                onClick    = { onRouteSelected(settingsItem.route) }
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// NavLogo  —  "N" box + animated "ETFLIX" text
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun NavLogo(isExpanded: Boolean) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Red "N" box
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(NetflixColors.Red, RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text       = "N",
                color      = NetflixColors.White,
                fontSize   = 22.sp,
                fontWeight = FontWeight.Black
            )
        }

        // "ETFLIX" slides in when expanded
        AnimatedVisibility(
            visible = isExpanded,
            enter   = fadeIn() + expandHorizontally(),
            exit    = fadeOut() + shrinkHorizontally()
        ) {
            Text(
                text       = "ETFLIX",
                color      = NetflixColors.Red,
                fontSize   = 22.sp,
                fontWeight = FontWeight.Black,
                modifier   = Modifier.padding(start = 2.dp)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// NavItemRow  —  red left bar + icon + animated label
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun NavItemRow(
    item: NavItem,
    isSelected: Boolean,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Background
    val bgColor = when {
        isSelected -> Color(0x33E50914)  // red tint
        isFocused  -> Color(0x1AFFFFFF)  // white tint
        else  -> Color(0x1A0C0404) /*Color.Transparent*/
    }

    // Left indicator bar
    val indicatorColor = if (isSelected) NetflixColors.Red else Color.Transparent

    // Icon + label colour
    val contentColor = when {
        isSelected || isFocused -> NetflixColors.White
        else                    -> NetflixColors.TextSecondary
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(bgColor)
            .clickable(
                interactionSource = interactionSource,
                indication        = null
            ) { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ── Red selection bar ─────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .width(3.dp)
                .height(24.dp)
                .background(indicatorColor, RoundedCornerShape(2.dp))
        )

        Spacer(Modifier.width(12.dp))

        // ── Icon ──────────────────────────────────────────────────────────────
        Icon(
            imageVector    = if (isSelected) item.selectedIcon else item.icon,
            contentDescription = item.label,
            tint           = contentColor,
            modifier       = Modifier.size(24.dp)
        )

        // ── Label (animated) ─────────────────────────────────────────────────
        AnimatedVisibility(
            visible = isExpanded,
            enter   = fadeIn() + expandHorizontally(),
            exit    = fadeOut() + shrinkHorizontally()
        ) {
            Text(
                text       = item.label,
                color      = contentColor,
                fontSize   = 15.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                modifier   = Modifier.padding(start = 14.dp)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// TopNavBar  —  horizontal top nav (alternative layout)
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun TopNavBar(
    isVisible: Boolean,
    currentRoute: String,
    onRouteSelected: (String) -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter   = fadeIn() + slideInVertically(),
        exit    = fadeOut() + slideOutVertically()
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color    = Color(0xE6000000)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp, vertical = 12.dp),
                verticalAlignment    = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Logo
                Text(
                    text       = "NETFLIX",
                    color      = NetflixColors.Red,
                    fontSize   = 24.sp,
                    fontWeight = FontWeight.Black,
                    modifier   = Modifier.padding(end = 24.dp)
                )

                navItems.forEach { item ->
                    TopNavItem(
                        item       = item,
                        isSelected = currentRoute == item.route,
                        onClick    = { onRouteSelected(item.route) }
                    )
                }

                Spacer(Modifier.weight(1f))

                Icon(
                    imageVector        = Icons.Default.Search,
                    contentDescription = "Search",
                    tint               = NetflixColors.White,
                    modifier           = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(16.dp))

                // Profile avatar
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(NetflixColors.Red, RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text       = "A",
                        color      = NetflixColors.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun TopNavItem(
    item: NavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication        = null
            ) { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text       = item.label,
            color      = if (isSelected || isFocused) NetflixColors.White
            else NetflixColors.TextSecondary,
            fontSize   = 15.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
        // Red underline when selected
        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(2.dp)
                    .background(NetflixColors.Red)
            )
        }
    }
}