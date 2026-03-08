package com.droid.ffdpboss.dashboard

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.droid.data.showToast
import com.droid.ffdpboss.R
import com.droid.ffdpboss.auth.AuthActivity
import com.droid.ffdpboss.auth.AuthViewModel
import com.droid.ffdpboss.home.HomeActivity
import com.droid.ffdpboss.home.navigation.HomeScreen
import com.droid.ffdpboss.reusableCard.verticalLineWithText
import com.droid.ffdpboss.ui.theme.tertiary
import com.droid.ffdpboss.ui.theme.textColor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfileScreen(navController: NavHostController, viewModel: AuthViewModel = getViewModel()) {
    var isBalanceVisible by remember { mutableStateOf(true) }
    var viewBalance by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val user by remember { viewModel.user }
    LaunchedEffect(Unit) {
        viewModel.fetchUserData()
    }
    val mutableInteractionSource = remember {
        MutableInteractionSource()
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController, HomeScreen.ProfileScreen) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    painterResource(R.drawable.profile_icon),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .clickable {
                            // navController.popBackStack()
                        }
                )
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = user?.name.orEmpty(), color = Color.Black,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "Mobile no: " + user?.userMobile.orEmpty(), color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 24.dp)
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                verticalLineWithText(if(isBalanceVisible) "View Balance" else "Available Balance ₹ ${viewBalance.orEmpty()}") {
                    scope.launch {
                        viewModel.onCheckBalance().collect {
                            if (it?.status == true) {
                                viewBalance = it.walletBalance
                            } else {
                                showToast(context, it?.message ?: "Something Went Wrong")
                            }
                            isBalanceVisible = !isBalanceVisible
                        }

                    }
                }
                verticalLineWithText("Change Password") {
                    navController.navigate(HomeScreen.ChangePasswordScreen.route)
                }
                verticalLineWithText("Game Rules") {
                    navController.navigate(HomeScreen.GameRulesScreen.route)
                }
                verticalLineWithText("About App") {

                }
                verticalLineWithText("Privacy Policy") {

                }

                Box(
                    modifier = Modifier
                        .clickable(
                            interactionSource = mutableInteractionSource,
                            indication = null
                        ) {
                            scope.launch {
                                viewModel
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
                        }
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .border(
                                width = 1.dp,
                                color = tertiary,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                            text = "Logout", color = textColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

        }
    }
}