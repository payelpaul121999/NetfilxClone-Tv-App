package com.droid.ffdpboss.gameScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.droid.data.model.homeModel.masterGameBajiModel.gameTypeResponse.MasterGameBajiType
import com.droid.ffdpboss.LargeText
import com.droid.ffdpboss.MediumText
import com.droid.ffdpboss.R
import com.droid.ffdpboss.SideDrawer
import com.droid.ffdpboss.TimeLeftCard
import com.droid.ffdpboss.ViewModel.HomeViewModel
import com.droid.ffdpboss.home.navigation.HomeScreen
import com.droid.ffdpboss.ui.theme.darkRed
import com.droid.ffdpboss.ui.theme.disabledButtonColor
import com.droid.ffdpboss.ui.theme.primary
import com.droid.ffdpboss.ui.theme.tertiary
import org.koin.androidx.compose.getViewModel


@Composable
fun GameTypeScreen(
    navController: NavController,
    gameId: String,
    screenName: String,
    screenId: String,
    viewModel: HomeViewModel = getViewModel(),
    closeHour: String
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.fetchMasterTypeData(gameId, screenId)
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
                Row(
                    modifier = Modifier.fillMaxWidth().background(color = primary),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = "Back"
                        )
                    }
                    LargeText(
                        modifier = Modifier.padding(start = 4.dp),
                        text = "Close",
                        color = Color.White,
                        fontSize = 16.sp,
                    )
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd){
                        TimeLeftCard(modifier = Modifier, time = closeHour)
                    }
                }
                LazyColumn(
                    modifier = Modifier.padding(top = 4.dp)
                ) { // Each row will have 2 items
                    items(viewModel.masterGameTypeList) { item ->
                        GameTypeItem(item) {
                            navController.navigate(
                                HomeScreen.AddBetScreen.withArgs(
                                    item.bajiTypeName,
                                    screenName,
                                    screenId,
                                    item.typeStat,
                                    closeHour
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
fun gameCard(item: MasterGameBajiType, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .height(200.dp)
            .width(200.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = disabledButtonColor, shape = RoundedCornerShape(20.dp)),

        contentAlignment = Alignment.Center
    ) {
        Column {
            Image(
                /* painterResource(R.drawable.single_patti)*/ rememberAsyncImagePainter(model = item?.photoPath),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .padding(10.dp)
                    .clickable {
                        onClick()
                        // navController.popBackStack()painter = rememberAsyncImagePainter(model =item.photoPath),
                    }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = item.bajiTypeName)
        }
    }
}


@Composable
fun GameTypeItem(item: MasterGameBajiType, onClick: () -> Unit) {
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
            model = item.photoPath,
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
                modifier = Modifier.padding(top = 4.dp),
                text = item.bajiTypeName,
                fontSize = 12.sp
            )
        }
        Button(
            modifier = Modifier.padding(end = 4.dp),
            onClick = {
                if (item.typeStat.toIntOrNull() == 1) {
                    onClick()
                }

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (item.typeStat.toIntOrNull() == 1) tertiary else darkRed,
            )
        ) {
            MediumText(
                modifier = Modifier,
                text = if (item.typeStat.toIntOrNull() == 1) "Play" else "Closed",
                color = Color.White
            )
        }
    }
}
