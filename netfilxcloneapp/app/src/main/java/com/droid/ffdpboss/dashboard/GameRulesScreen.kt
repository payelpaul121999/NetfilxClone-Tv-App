package com.droid.ffdpboss.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.droid.data.model.rulesModel.GameRules
import com.droid.ffdpboss.LargeBoldText
import com.droid.ffdpboss.LargeText
import com.droid.ffdpboss.MediumText
import com.droid.ffdpboss.MyTopAppBar
import com.droid.ffdpboss.SideDrawer
import com.droid.ffdpboss.ViewModel.HomeViewModel
import com.droid.ffdpboss.home.navigation.HomeScreen
import com.droid.ffdpboss.ui.theme.primary
import com.droid.ffdpboss.ui.theme.tertiary
import com.droid.ffdpboss.ui.theme.textColor
import org.koin.androidx.compose.getViewModel

@Composable
fun GameRulesScreen(navController: NavController,homeViewModel: HomeViewModel = getViewModel()){
    LaunchedEffect(Unit) {
        homeViewModel.onFetchGameRules()
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
                        text = "GAME RULES",
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items( homeViewModel.gameRulesList) { ruleItmes ->
                    RulesCard(game = ruleItmes)
                }
            }
        }

    })
}


@Composable
private fun RulesCard(game: GameRules){
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .background(color = primary),
            contentAlignment = Alignment.Center
        ) {
            LargeBoldText(
                modifier = Modifier.padding(vertical = 12.dp),
                text = game.rulesTitle,
                color = Color.White,
                fontSize = 16.sp,
            )
        }
        MediumText(modifier = Modifier.padding(top = 8.dp), text = game.title1, color = tertiary)
        MediumText(modifier = Modifier.padding(top = 8.dp), text = game.title2, color = tertiary)
        MediumText(modifier = Modifier.padding(top = 8.dp), text =  game.title3, color = tertiary)
        MediumText(modifier = Modifier.padding(top = 8.dp), text =  game.title4, color = tertiary)
    }
}