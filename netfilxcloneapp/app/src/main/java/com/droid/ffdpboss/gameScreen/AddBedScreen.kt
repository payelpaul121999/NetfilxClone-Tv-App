package com.droid.ffdpboss.gameScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.droid.data.isValidAmount
import com.droid.data.model.betModel.AddBetModelRequest
import com.droid.data.model.betModel.BettingDraftList
import com.droid.data.showToast
import com.droid.ffdpboss.LargeText
import com.droid.ffdpboss.MediumText
import com.droid.ffdpboss.R
import com.droid.ffdpboss.SideDrawer
import com.droid.ffdpboss.TimeLeftCard
import com.droid.ffdpboss.ViewModel.HomeViewModel
import com.droid.ffdpboss.data.DataPreferences
import com.droid.ffdpboss.data.getBetTitle
import com.droid.ffdpboss.home.navigation.HomeScreen
import com.droid.ffdpboss.ui.theme.darkRed
import com.droid.ffdpboss.ui.theme.greyColor
import com.droid.ffdpboss.ui.theme.secondary
import com.droid.ffdpboss.ui.theme.tertiary
import com.droid.ffdpboss.ui.theme.textColor
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddBedScreen(
    navController: NavController,
    gameType: String,
    gameMasterSl: String,
    gameMasterBajiSl: String,
    gameMasterBajiType: String,
    closeHour: String,
    viewModel: GameScreenViewModel = getViewModel(),
    homeViewModel: HomeViewModel = getViewModel()
) {
    val context = LocalContext.current
    var balance by remember { homeViewModel.balance }
    var amountNumber by remember { mutableStateOf("") }
    var digitNumber by remember { mutableStateOf("") }
    val gameList = getBetTitle()
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        homeViewModel.onCheckBalance()
    }
    LaunchedEffect(Unit) {
        AddBetModelRequest(
            apikey = DataPreferences(context).getApiKey().orEmpty(),
            gameMasterBajiSl = gameMasterSl,
            gameMasterBajiType = gameMasterBajiSl,
            gameMasterSl = gameNameOfDigit(gameType).toString()
        ).also {
            viewModel.fetchGameAddedBettingList(it)
        }
    }
    fun clearInputs() {
        digitNumber = ""
        amountNumber = ""
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
                    .fillMaxWidth()
                    .background(color = tertiary)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
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
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                        TimeLeftCard(modifier = Modifier, time = closeHour)
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .background(color = Color.White)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .weight(1f)
                                .border(
                                    BorderStroke(1.dp, color = greyColor),
                                    shape = RoundedCornerShape(8.dp),
                                )
                                .background(color = Color.White, shape = RoundedCornerShape(8.dp)),
                            value = digitNumber,
                            onValueChange = {
                                if (it.length <= gameNameOfDigit(gameType)) {
                                    digitNumber = it
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            placeholder = {
                                MediumText(
                                    modifier = Modifier,
                                    text = "Enter Digit",
                                    color = Color.LightGray
                                )
                            },
                            singleLine = true,
                            maxLines = 1

                        )
                        TextField(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .weight(1f)
                                .border(
                                    BorderStroke(1.dp, color = greyColor),
                                    shape = RoundedCornerShape(8.dp),
                                )
                                .background(color = Color.White, shape = RoundedCornerShape(8.dp)),
                            value = amountNumber,
                            onValueChange = {
                                amountNumber = it
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            placeholder = {
                                MediumText(
                                    modifier = Modifier,
                                    text = "Enter Rs",
                                    color = Color.LightGray
                                )
                            },
                            maxLines = 1,
                            textStyle = TextStyle(
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            )
                        )
                    }

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = tertiary,
                        ), onClick = {
                            scope.launch {
                                val apikey = DataPreferences(context).getApiKey().orEmpty()
                                val addBetModelRequest = AddBetModelRequest(
                                    apikey = apikey,
                                    betAmount = amountNumber,
                                    digitNo = digitNumber,
                                    gameMasterBajiSl = gameMasterSl,
                                    gameMasterBajiType = gameMasterBajiSl,
                                    gameMasterSl = gameNameOfDigit(gameType).toString()
                                )
                                if (gameType == "Single") {
                                    if (gameType == "Single" && digitNumber.length == 1 && isValidAmount(
                                            amountNumber
                                        )
                                    ) {

                                        homeViewModel.fetchGameBettingDraftAddUrl(
                                            addBetModelRequest
                                        ).collect {
                                            if (it?.Status == true) {
                                                clearInputs()
                                                showToast(context, it.Message.orEmpty())
                                                viewModel.fetchGameAddedBettingList(
                                                    addBetModelRequest
                                                )
                                            } else {
                                                showToast(context, it?.Message.orEmpty())
                                            }
                                        }

                                    } else if (!isValidAmount(amountNumber)) {
                                        showToast(context, "Please enter valid Amount")
                                    } else if (digitNumber.length > 1) {
                                        showToast(context, "Please enter valid digit")
                                    } else {
                                        showToast(context, "Please enter valid details")
                                    }
                                } else if (gameType == "Patti") {
                                    if (gameType == "Patti" && digitNumber.length == 3 && isValidAmount(
                                            amountNumber
                                        )
                                    ) {
                                        showToast(context, "Successfully Added")
                                        homeViewModel.fetchGameBettingDraftAddUrl(
                                            addBetModelRequest
                                        ).collect {
                                            if (it?.Status == true) {
                                                clearInputs()
                                                showToast(context, it.Message.orEmpty())
                                                viewModel.fetchGameAddedBettingList(
                                                    addBetModelRequest
                                                )
                                            } else {
                                                showToast(context, it?.Message.orEmpty())
                                            }
                                        }
                                    } else if (!isValidAmount(amountNumber)) {
                                        showToast(context, "Please enter valid Amount")
                                    } else if (digitNumber.length > 3) {
                                        showToast(context, "Please enter valid digit")
                                    } else {
                                        showToast(context, "Please enter valid details")
                                    }
                                } else if (gameType == "Jodi") {
                                    if (gameType == "Jodi" && digitNumber.length == 2 && isValidAmount(
                                            amountNumber
                                        )
                                    ) {
                                        showToast(context, "Successfully Added")
                                        homeViewModel.fetchGameBettingDraftAddUrl(
                                            AddBetModelRequest(
                                                apikey, amountNumber, digitNumber, gameMasterSl,
                                                gameMasterBajiSl,
                                                gameNameOfDigit(gameType).toString()
                                            )
                                        ).collect {
                                            if (it?.Status == true) {
                                                clearInputs()
                                                showToast(context, it.Message.orEmpty())
                                                viewModel.fetchGameAddedBettingList(
                                                    addBetModelRequest
                                                )
                                            } else {
                                                showToast(context, it?.Message.orEmpty())
                                            }
                                        }
                                    } else if (!isValidAmount(amountNumber)) {
                                        showToast(context, "Please enter valid Amount")
                                    } else if (digitNumber.length > 2) {
                                        showToast(context, "Please enter valid digit")
                                    } else {
                                        showToast(context, "Please enter valid details")
                                    }
                                } else if (gameType == "CP") {
                                    if (gameType == "CP" && (digitNumber.length == 4 || digitNumber.length == 5) && isValidAmount(
                                            amountNumber
                                        )
                                    ) {
                                        showToast(context, "Successfully Added")
                                        homeViewModel.fetchGameBettingDraftAddUrl(
                                            AddBetModelRequest(
                                                apikey, amountNumber, digitNumber, gameMasterSl,
                                                gameMasterBajiSl,
                                                gameNameOfDigit(gameType).toString()
                                            )
                                        ).collect {
                                            if (it?.Status == true) {
                                                clearInputs()
                                                showToast(context, it.Message.orEmpty())
                                                viewModel.fetchGameAddedBettingList(
                                                    addBetModelRequest
                                                )
                                            } else {
                                                showToast(context, it?.Message.orEmpty())
                                            }
                                        }
                                    } else if (!isValidAmount(amountNumber)) {
                                        showToast(context, "Please enter valid Amount")
                                    } else if (digitNumber.length > 5) {
                                        showToast(context, "Please enter valid digit")
                                    } else {
                                        showToast(context, "Please enter valid details")
                                    }
                                }
                            }
                        }) {
                        MediumText(
                            text = "Add Bet",
                            modifier = Modifier.padding(2.dp),
                            color = Color.White
                        )
                    }
                }
            }

            if (viewModel.betList.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth()
                        .background(color = tertiary)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LargeText(
                            modifier = Modifier.padding(top = 10.dp),
                            text = "Booking List",
                            color = Color.White,
                            fontSize = 16.sp,
                        )
                    }
                    viewModel.betList.forEach {
                        BookingListItem(it) {

                        }
                    }

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .padding(top = 4.dp, bottom = 10.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = secondary,
                        ), onClick = {
                            scope.launch {
                                val addBetModelRequest = AddBetModelRequest(
                                    apikey = DataPreferences(context).getApiKey().orEmpty(),
                                    gameMasterSl = gameMasterSl,
                                    gameMasterBajiSl = gameMasterBajiSl,
                                    gameMasterBajiType = gameNameOfDigit(gameType).toString()
                                )
                                homeViewModel.submitBet(addBetModelRequest).collect {
                                    if (it?.Status == true) {
                                        showToast(context, it.Message.orEmpty())
                                        viewModel.fetchGameAddedBettingList(
                                            addBetModelRequest
                                        )
                                        clearInputs()
                                        homeViewModel.onCheckBalance()
                                    } else {
                                        showToast(context, it?.Message.orEmpty())
                                    }
                                }
                            }
                        }) {
                        MediumText(
                            text = "Play Now",
                            modifier = Modifier.padding(2.dp),
                            color = Color.White
                        )
                    }
                }
            }
        }

    })


}

@Composable
private fun BookingListItem(bettingDraftList: BettingDraftList, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MediumText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                text = "Number : ${bettingDraftList.betAmount}",
                color = textColor
            )
            MediumText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                text = "Coin : ${bettingDraftList.betDigit}",
                color = textColor
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .background(color = darkRed, shape = RoundedCornerShape(20.dp))
                .clickable { onDelete() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.padding(vertical = 6.dp),
                painter = painterResource(id = R.drawable.ic_delete),
                tint = Color.White,
                contentDescription = "Back"
            )
        }
    }
}


fun gameNameOfDigit(game: String): Int {
    return when (game) {
        "Single" -> 1
        "Patti" -> 3
        "Jodi" -> 2
        "CP" -> 4
        else -> 0
    }
}


