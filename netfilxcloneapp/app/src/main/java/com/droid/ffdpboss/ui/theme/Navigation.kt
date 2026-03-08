package com.droid.ffdpboss.ui.theme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.droid.ffdpboss.R

data class BottonNavItem(
    val title :String,
    val route:String,
    val seletedIcon : Int,
    val unseletedIcon : Int,
    val hasNews : Boolean,
    val badges:Int
)

val bottomNavItem = listOf(
    BottonNavItem(
        title = "Home",
        route = "home",
        seletedIcon= R.drawable.home_unselected,
        unseletedIcon = R.drawable.home_select,
        hasNews = false,
        badges = 0
    ),
    BottonNavItem(
        title = "My Bid",
        route = "myprediction",
        seletedIcon= R.drawable.game_unselect,
        unseletedIcon =  R.drawable.game_selected,
        hasNews = false,
        badges = 2
    ),
   /* BottonNavItem(
        title = "myprediction",
        route = "myprediction",
        seletedIcon= Icons.Filled.Star,
        unseletedIcon = Icons.Outlined.Star,
        hasNews = false,
        badges = 2
    ),BottonNavItem(
        title = "Win",
        route = "win",
        seletedIcon= Icons.Filled.Notifications,
        unseletedIcon = Icons.Outlined.Notifications,
        hasNews = true,
        badges = 2
    ),
    BottonNavItem(
        title = "Contact",
        route = "contact",
        seletedIcon= Icons.Filled.Share,
        unseletedIcon = Icons.Outlined.Share,
        hasNews = false,
        badges = 0
    ),*/
    BottonNavItem(
        title = "Profile",
        route = "profile",
        seletedIcon= R.drawable.profile_unselect,
        unseletedIcon = R.drawable.profile_select,
        hasNews = false,
        badges = 0
    ),
    /*profile*/
)