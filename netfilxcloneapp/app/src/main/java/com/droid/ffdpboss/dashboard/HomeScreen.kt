package com.droid.ffdpboss.dashboard


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.droid.data.model.homeModel.MasterGame
import com.droid.data.model.homeModel.masterGameBajiModel.MasterGameBaji
import com.droid.ffdpboss.AutoImageSliderComponent
import com.droid.ffdpboss.LargeText
import com.droid.ffdpboss.MediumText
import com.droid.ffdpboss.NormalText
import com.droid.ffdpboss.R
import com.droid.ffdpboss.SideDrawer
import com.droid.ffdpboss.ViewModel.HomeViewModel
import com.droid.ffdpboss.data.DataPreferences
import com.droid.ffdpboss.home.navigation.HomeScreen
import com.droid.ffdpboss.ui.theme.darkRed
import com.droid.ffdpboss.ui.theme.primary
import com.droid.ffdpboss.ui.theme.tertiary
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.androidx.compose.getViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

data class NavigationItem(
    val title: String,
    val route: String,
    val showImage: Int,
    /*val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,*/
    val badgeCount: Int? = null
)

@Composable
fun HomeScreenPage(
    navController: NavController,
    viewModel: HomeViewModel = getViewModel()
) {
    val context = LocalContext.current
    val isLoading = remember { viewModel.isLoading }
    val marqueeText = remember { viewModel.marqueeText }
    val isQuickActionDialogOpened = remember {
        mutableStateOf(false)
    }
    SideDrawer(navController = navController, screen = HomeScreen.Home, content = {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(top = 8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading.value) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MediumText(
                        modifier = Modifier.padding(top = 8.dp),
                        text = "Please Wait",
                        color = tertiary
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxWidth()
                        .background(color = tertiary)
                ) {
                    MediumText(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .basicMarquee(
                                iterations = Int.MAX_VALUE
                            ),
                        text = marqueeText.value,
                        singleLine = true,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                AutoImageSliderComponent(images = viewModel.bannerList)
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val homePageButtonsWithAction =
                        listOf("Balance", "Withdraw", "Quick", "Transaction")
                    homePageButtonsWithAction.forEachIndexed { index, data ->
                        homePageButton(index, homePageButtonsWithAction.size, data) {
                            when (data) {
                                "Balance" -> {
                                    navController.navigate(HomeScreen.AddBalanceScreen.withArgs("ADD MONEY",0))
                                }

                                "Withdraw" -> {
                                    navController.navigate(HomeScreen.AddBalanceScreen.withArgs("WITHDRAW WINNINGS",1))
                                }

                                "Quick" -> {
                                    isQuickActionDialogOpened.value = true

                                }

                                "Transaction" -> {
                                    navController.navigate(HomeScreen.TransactionListScreen.route)
                                }
                            }
                        }
                    }
                }
                if (viewModel.masterGameList.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                            .background(color = tertiary)
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = primary),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            LargeText(
                                modifier = Modifier.padding(vertical = 10.dp),
                                text = "Games List",
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                        viewModel.masterGameList.forEach {
                            GameCardItem(it) {
                                val encodedUrl = URLEncoder.encode(
                                    it.gamePhotoPath,
                                    StandardCharsets.UTF_8.toString()
                                )
                                navController.navigate(
                                    HomeScreen.GameListScreen.withArgs(
                                        it.gameSl, it.gameName, encodedUrl
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

            }

        }
    })

    if(isQuickActionDialogOpened.value){
        QuickActionAlertDialog(onDismiss = {
            isQuickActionDialogOpened.value = false
        }, onNavigate = {
            navController.navigate(it)
            isQuickActionDialogOpened.value = false
        })
    }


    LaunchedEffect(Unit) {
        val apiKey = DataPreferences(context).getApiKey()
        viewModel.fetchHomeData(apiKey)
    }

}

@Composable
private fun RowScope.homePageButton(
    index: Int,
    totalItemSize: Int,
    text: String,
    action: () -> Unit
) {
    val padding = if (index == 0) Modifier.padding(
        end = 2.dp
    ) else if (index == totalItemSize - 1) Modifier.padding(
        start = 2.dp
    ) else Modifier.padding(start = 2.dp, end = 2.dp)
    Box(
        modifier = padding
            .weight(1f)
            .background(color = tertiary, shape = RoundedCornerShape(4.dp))
            .clickable {
                action()
            },
        contentAlignment = Alignment.Center
    ) {
        MediumText(
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 12.dp),
            text = text,
            color = Color.White,
            fontSize = 8.sp
        )
    }
}


@Composable
fun GameCardItem(gameBody: MasterGame?, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(4.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(start = 4.dp)
                .padding(vertical = 8.dp)
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp)),
            model = gameBody?.gamePhotoPath,
            placeholder = painterResource(id = R.drawable.app_icon),
            contentDescription = ""
        )
        Column(
            modifier = Modifier
                .padding(start = 6.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            MediumText(
                modifier = Modifier,
                color = darkRed,
                text = gameBody?.gameTitle.orEmpty(),
                fontSize = 14.sp,
                singleLine = true
            )
            MediumText(
                modifier = Modifier.padding(top = 4.dp),
                text = gameBody?.gameName.orEmpty(),
                fontSize = 12.sp,
                singleLine = true
            )
        }
        Button(
            modifier = Modifier.padding(end = 4.dp),
            onClick = {
                onClick()

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = tertiary,
            )
        ) {
            MediumText(
                modifier = Modifier,
                text = "Play",
                color = Color.White
            )
        }
    }
}

