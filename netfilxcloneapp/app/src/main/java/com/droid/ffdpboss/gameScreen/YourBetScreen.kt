package com.droid.ffdpboss.gameScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.droid.data.model.bids.GameBidGameName
import com.droid.data.model.homeModel.masterGameBajiModel.MasterGameBaji
import com.droid.ffdpboss.LargeText
import com.droid.ffdpboss.MediumText
import com.droid.ffdpboss.R
import com.droid.ffdpboss.SideDrawer
import com.droid.ffdpboss.ViewModel.HomeViewModel
import com.droid.ffdpboss.data.DataPreferences
import com.droid.ffdpboss.home.navigation.HomeScreen
import com.droid.ffdpboss.ui.theme.darkRed
import com.droid.ffdpboss.ui.theme.primary
import com.droid.ffdpboss.ui.theme.tertiary
import org.koin.androidx.compose.getViewModel

@Composable
fun YourBetScreen(navController: NavController, viewModel: HomeViewModel = getViewModel()) {
    /*Scaffold(
        topBar = {

        },
        bottomBar = { BottomNavigationBar(navController, HomeScreen.YourBetScreen) }
    ) {*/
    val myBids = remember {
        viewModel.myBids
    }
    LaunchedEffect(Unit) {
        viewModel.fetchGameResults()
    }
    SideDrawer(navController = navController, screen = HomeScreen.ContactUsScreen, content = {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 12.dp)
                    .fillMaxWidth()
                    .wrapContentSize()
                    .background(color = tertiary)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = primary),
                    contentAlignment = Alignment.Center
                ) {
                    LargeText(
                        modifier = Modifier,
                        text = "MY BIDS",
                        color = Color.White,
                        fontSize = 16.sp,
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                tint = Color.White,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier.padding(top = 4.dp)
                ) { // Each row will have 2 items
                    items(myBids) {
                        GameBidCardItem(it) {
                            navController.navigate(
                                HomeScreen.GameWiseBidResultScreen.withArgs(
                                    it.gameId
                                )
                            )
                        }
                    }
                }
            }


        }

    })


}

@Composable
private fun GameBidCardItem(gameBidGameName: GameBidGameName, onClick: () -> Unit) {
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
            model = gameBidGameName.gameImage,
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
                text = gameBidGameName.gameName,
                fontSize = 14.sp
            )
            MediumText(
                modifier = Modifier.padding(top = 4.dp),
                text = gameBidGameName.gameDescription,
                fontSize = 12.sp
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
                text = "View",
                color = Color.White
            )
        }
    }
}
