package com.droid.ffdpboss.gameScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.droid.data.model.bids.GameBidResultitem
import com.droid.data.model.homeModel.TransactionListItem
import com.droid.ffdpboss.LargeText
import com.droid.ffdpboss.MediumText
import com.droid.ffdpboss.NormalText
import com.droid.ffdpboss.SideDrawer
import com.droid.ffdpboss.ViewModel.HomeViewModel
import com.droid.ffdpboss.dashboard.GameTypes
import com.droid.ffdpboss.home.navigation.HomeScreen
import com.droid.ffdpboss.ui.theme.primary
import com.droid.ffdpboss.ui.theme.tertiary
import com.droid.ffdpboss.ui.theme.textColor
import org.koin.androidx.compose.getViewModel

@Composable
fun GameWiseBidScreen(
    navController: NavController,
    gameId: String,
    viewModel: HomeViewModel = getViewModel()
) {
    val myBids = remember {
        viewModel.myBids
    }
    val gameWiseBidResult = remember {
        viewModel.gameWiseBidResult
    }
    val gameTypes = remember {
        viewModel.gameTypes
    }
    LaunchedEffect(Unit) {
        viewModel.fetchEachGameBids(gameId = gameId)
    }
    SideDrawer(navController = navController, screen = HomeScreen.ContactUsScreen, content = {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top
            ) {
                gameTypes.forEach {
                    GameBidTabItem(
                        itemName = it.gameType,
                        itemId = it.gameId,
                        isSelected = it.isSelected,
                        onItemClick = {
                            viewModel.updateGameTypeSelection(it)
                        })
                }

            }
            GameBidHeaderItem()
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(gameWiseBidResult) {
                    Log.i("JAPAN", "GameWiseBidScreen: ${gameWiseBidResult}")
                    GameBidItem(gameBidResultitem = it)
                }
            }

        }

    })


}

@Composable
private fun RowScope.GameBidTabItem(
    itemName: String,
    itemId: Int,
    isSelected: Boolean,
    onItemClick: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .weight(1f)
            .clickable {

            }, horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = { onItemClick(itemId) })
        MediumText(
            modifier = Modifier
                .padding(start = 8.dp)
                .padding(vertical = 12.dp),
            text = itemName,
            color = textColor,
            fontSize = 10.sp
        )
    }
}


@Composable
private fun GameBidHeaderItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .background(color = tertiary),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MediumText(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .padding(vertical = 4.dp),
            text = "Date",
            color = Color.White,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
        MediumText(
            modifier = Modifier.weight(1f),
            text = "Baji",
            color = Color.White,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
        MediumText(
            modifier = Modifier.weight(1f),
            text = "Number",
            color = Color.White,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
        MediumText(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .padding(vertical = 4.dp),
            text = "Amount",
            color = Color.White,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
        MediumText(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .padding(vertical = 4.dp),
            text = "Result",
            color = Color.White,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun GameBidItem(gameBidResultitem: GameBidResultitem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NormalText(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .padding(vertical = 4.dp),
            text = gameBidResultitem.betdate.orEmpty(),
            color = textColor,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
        NormalText(
            modifier = Modifier.weight(1f),
            text = gameBidResultitem.bajiName.orEmpty(),
            color = textColor,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
        NormalText(
            modifier = Modifier.weight(1f),
            text = gameBidResultitem.digitNo.orEmpty(),
            color = textColor,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
        NormalText(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .padding(vertical = 4.dp),
            text = gameBidResultitem.winStat.orEmpty(),
            color = textColor,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
    }
}