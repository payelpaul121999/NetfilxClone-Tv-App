package com.droid.ffdpboss.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.droid.ffdpboss.NormalText
import com.droid.ffdpboss.home.navigation.HomeScreen
import com.droid.ffdpboss.ui.theme.bottomNavItem
import com.droid.ffdpboss.ui.theme.gradient2Color
import com.droid.ffdpboss.ui.theme.tertiary
import com.droid.ffdpboss.ui.theme.textColor


@Composable
fun BottomNavigationBar(navController: NavController, screen: HomeScreen) {
    val items = listOf(
        HomeScreen.Home,
        HomeScreen.YourBetScreen,
        HomeScreen.ResultScreen,
        HomeScreen.ContactUsScreen,
        HomeScreen.ProfileScreen,
    )
    val selectedShape = RoundedCornerShape(25)
    val unselectedShape = RoundedCornerShape(0)
    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = tertiary),
        backgroundColor = tertiary,
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (item == HomeScreen.ResultScreen) {
                            Box(
                                modifier = Modifier.background(
                                    color = gradient2Color,
                                    shape = CircleShape
                                ), contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier.padding(16.dp),
                                    painter = painterResource(id = if (screen.route == item.route) screen.selectedIcon!! else item.unSelectedIcon!!),
                                    contentDescription = item.route,
                                    tint = Color.White
                                )
                            }
                        } else {
                            Icon(
                                painter = painterResource(id = if (screen.route == item.route) screen.selectedIcon!! else item.unSelectedIcon!!),
                                contentDescription = item.route,
                                tint = Color.White
                            )
                            NormalText(modifier = Modifier, text = item.name.orEmpty(), color = Color.White, fontSize = 12.sp)
                        }
                    }
                },
                alwaysShowLabel = false,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                },
                modifier = Modifier
                    .padding(1.dp)
                    .clip(if (screen.route == item.route) selectedShape else unselectedShape)
                    .background(if (screen.route == item.route) tertiary.copy(alpha = 0.09f) else Color.Transparent)
            )
        }
    }
}