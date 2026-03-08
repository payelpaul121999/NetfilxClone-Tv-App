package com.droid.ffdpboss.dashboard

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.droid.data.model.resultModel.GameResultItem
import com.droid.ffdpboss.LargeBoldText
import com.droid.ffdpboss.LargeText
import com.droid.ffdpboss.MediumText
import com.droid.ffdpboss.NormalText
import com.droid.ffdpboss.SideDrawer
import com.droid.ffdpboss.home.navigation.HomeScreen
import com.droid.ffdpboss.ui.theme.darkRed
import com.droid.ffdpboss.ui.theme.primary
import com.droid.ffdpboss.ui.theme.tertiary
import com.droid.ffdpboss.ui.theme.textColor
import org.koin.androidx.compose.getViewModel

@Composable
fun GameResultScreen(
    gameType: String,
    navController: NavController,
    viewModel: WinScreenViewModel = getViewModel()
) {
    val gameResultsBasedOnGameTypes = remember {
        viewModel.gameResultsBasedOnGameTypes
    }
    val gameTypes = remember {
        viewModel.gameTypes
    }
    val result = remember {
        mutableStateListOf<GameResultItem>()
    }
    LaunchedEffect(Unit) {
        viewModel.fetchEachGameResults(gameType)
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
                    .background(color = primary)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = primary),
                    contentAlignment = Alignment.Center
                ) {
                    LargeText(
                        modifier = Modifier,
                        text = "GAME RESULT",
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
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top
            ) {
                gameTypes.forEach {
                    TabItem(
                        itemName = it.gameType,
                        itemId = it.gameId,
                        isSelected = it.isSelected,
                        onItemClick = {
                            viewModel.updateGameTypeSelection(it)
                        })
                }

            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(it),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(gameResultsBasedOnGameTypes) {result->
                    GameTypes(result)
                }

            }
        }

    })

}

@Composable
private fun RowScope.TabItem(
    itemName: String,
    itemId: Int,
    isSelected: Boolean,
    onItemClick: (Int) -> Unit,
) {
    val color = if (isSelected) tertiary else tertiary.copy(alpha = 0.1f)
    val textColor = if (isSelected) Color.White else textColor
    val fontSize = 10.sp
    Box(
        modifier = Modifier
            .weight(1f)
            .background(color = color)
            .clickable {
                onItemClick(itemId)
            }, contentAlignment = Alignment.Center
    ) {
        MediumText(
            modifier = Modifier.padding(vertical = 12.dp),
            text = itemName,
            color = textColor,
            fontSize = 10.sp
        )
    }
}

@Composable
fun GameTypes(gameResultGameType: List<GameResultItem>) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 8.dp)
            .background(color = tertiary.copy(alpha = 0.3f), shape = RoundedCornerShape(12.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LargeBoldText(
            modifier = Modifier.padding(top = 2.dp),
            text = gameResultGameType.firstOrNull()?.betdate.orEmpty(),
            fontSize = 12.sp
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(gameResultGameType) {
                GameResultCardItem(it)
            }
        }
    }
}

@Composable
private fun GameResultCardItem(result: GameResultItem) {
    Card(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MediumText(
                modifier = Modifier,
                text = result.singleWinDigitNo.orEmpty(),
                fontSize = 10.sp,
                color = darkRed
            )
            NormalText(
                modifier = Modifier,
                text = result.pattiWinDigitNo.orEmpty(),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}


