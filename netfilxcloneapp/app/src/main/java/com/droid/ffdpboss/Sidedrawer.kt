package com.droid.ffdpboss

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droid.data.showToast
import com.droid.ffdpboss.auth.AuthActivity
import com.droid.ffdpboss.auth.AuthViewModel
import com.droid.ffdpboss.dashboard.BottomNavigationBar
import com.droid.ffdpboss.dashboard.NavigationItem
import com.droid.ffdpboss.home.HomeActivity
import com.droid.ffdpboss.home.navigation.HomeScreen
import com.droid.ffdpboss.reusableCard.ProfileCard
import com.droid.ffdpboss.reusableCard.checkBalance
import com.droid.ffdpboss.ui.theme.primary
import com.droid.ffdpboss.ui.theme.tertiary
import com.droid.ffdpboss.ui.theme.textColor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SideDrawer(
    navController: NavController,
    screen: HomeScreen,
    content: @Composable (paddingValues: PaddingValues) -> Unit,
    authViewModel: AuthViewModel = getViewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    var isBalanceVisible by remember { mutableStateOf(true) }
    var viewBalance by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val user by remember { authViewModel.user }
    LaunchedEffect(Unit) {
        authViewModel.fetchUserData()
    }
    ModalNavigationDrawer(
        modifier = Modifier.background(color = tertiary),
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = tertiary
            ) {
                ProfileCard("${user?.name}", "${user?.userMobile}") {
                    scope.launch {
                        drawerState.close()
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(modifier = Modifier.weight(1f)) {
                    itemsIndexed(navigationItems) { index, item ->
                        CustomNavigationItem(icon = item.showImage, title = item.title) {
                            selectedItemIndex = index
                            scope.launch {
                                if (item.route == "") {
                                    drawerState.close()
                                } else if (item.route == "logout") {
                                    scope.launch {
                                        authViewModel
                                            .onLogOut()
                                            .collectLatest {
                                                (context as HomeActivity).finish()
                                                context.startActivity(
                                                    Intent(
                                                        context,
                                                        AuthActivity::class.java
                                                    )
                                                )
                                            }
                                    }
                                } else if (item.route == "share") {
                                    val uri =
                                        Uri.parse("https://api.whatsapp.com/send?phone=9876543251&text=Play Game Now")
                                    val intent = Intent(Intent.ACTION_VIEW, uri)
                                    intent.setPackage("com.whatsapp")
                                    if (intent.resolveActivity(context.packageManager) != null) {
                                        context.startActivity(intent)
                                    } else {
                                        // Handle the case where WhatsApp is not installed
                                    }
                                } else {
                                    navController.navigate(item.route)
                                }

                            }
                        }
                    }
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        MediumText(modifier = Modifier, color = Color.White, text = "FFDpboss")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = tertiary),
                    actions = {
                        checkBalance()
                    }
                )
            },

            bottomBar = { BottomNavigationBar(navController, screen) }
        ) {
            content(it)
        }
    }
}


private val navigationItems = listOf(
    NavigationItem(
        title = "Home",
        HomeScreen.Home.route,
        R.drawable.home_select
    ),
    NavigationItem(
        title = "Add Balance",
        HomeScreen.AddBalanceScreen.withArgs("ADD MONEY", 0),
        R.drawable.add_balance
        /* selectedIcon = Icons.Filled.Info,
         unselectedIcon = Icons.Outlined.Info,*/
    ),
    NavigationItem(
        title = "Withdraw Balance",
        HomeScreen.AddBalanceScreen.withArgs("WITHDRAW WINNINGS", 1),
        R.drawable.withdraw_bal
        /* selectedIcon = Icons.Filled.Settings,
         unselectedIcon = Icons.Outlined.Settings,*/
    ),
    NavigationItem(
        title = "My Bet",
        HomeScreen.YourBetScreen.route,
        R.drawable.my_bet_icon
        /* selectedIcon = Icons.Filled.Settings,
         unselectedIcon = Icons.Outlined.Settings,*/
    ),
    NavigationItem(
        title = "Transaction History",
        HomeScreen.TransactionListScreen.route,
        R.drawable.transaction_bal
        /* selectedIcon = Icons.Filled.Settings,
         unselectedIcon = Icons.Outlined.Settings,*/
    ),
    NavigationItem(
        title = "Game Timing",
        "",
        R.drawable.time_game
        /* selectedIcon = Icons.Filled.Settings,
         unselectedIcon = Icons.Outlined.Settings,*/
    ),
    NavigationItem(
        title = "Game Rules",
        HomeScreen.GameRulesScreen.route,
        R.drawable.rules_icon
        /* selectedIcon = Icons.Filled.Settings,
         unselectedIcon = Icons.Outlined.Settings,*/
    ),
    NavigationItem(
        title = "Support",
        "",
        R.drawable.support_icon
        /* selectedIcon = Icons.Filled.Settings,
         unselectedIcon = Icons.Outlined.Settings,*/
    ),
    NavigationItem(
        title = "Share",
        "share",
        R.drawable.share
        /* selectedIcon = Icons.Filled.Settings,
         unselectedIcon = Icons.Outlined.Settings,*/
    ),
    NavigationItem(
        title = "Logout",
        "logout",
        R.drawable.logout
        /* selectedIcon = Icons.Filled.Settings,
         unselectedIcon = Icons.Outlined.Settings,*/
    )
)

@Composable
private fun CustomNavigationItem(icon: Int, title: String, onClicked: () -> Unit) {
    val mutableInteractionSource = remember {
        MutableInteractionSource()
    }
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = mutableInteractionSource,
                indication = null
            ) { onClicked() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier.background(color = Color.White, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .padding(6.dp)
                    .size(24.dp),
                painter = painterResource(id = icon),
                contentDescription = "Navigation Icon",
                tint = textColor
            )
        }
        MediumText(modifier = Modifier.padding(start = 6.dp), text = title, color = Color.White)
    }
}