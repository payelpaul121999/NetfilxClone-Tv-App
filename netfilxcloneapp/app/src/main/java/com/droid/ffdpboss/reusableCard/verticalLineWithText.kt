package com.droid.ffdpboss.reusableCard

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droid.data.showToast
import com.droid.ffdpboss.MediumText
import com.droid.ffdpboss.R
import com.droid.ffdpboss.auth.AuthViewModel
import com.droid.ffdpboss.ui.theme.secondary
import com.droid.ffdpboss.ui.theme.tertiary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import kotlin.time.Duration.Companion.seconds

@Composable
fun verticalLineWithText(text: String, onClick: () -> Unit) {
    Column(modifier = Modifier
        .padding(top = 10.dp)
        .clickable {
            onClick()
        }) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = secondary)
        )
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = text, color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun checkBalance(
    authViewModel: AuthViewModel = getViewModel()
) {
    var isBalanceVisible by remember { mutableStateOf(true) }
    var viewBalance by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        authViewModel
            .onCheckBalance()
            .collect {
                if (it?.status == true) {
                    viewBalance = it.walletBalance
                } else {
                    showToast(
                        context,
                        it?.message ?: "Something Went Wrong"
                    )
                }
                isBalanceVisible = !isBalanceVisible
            }
    }

    var isRotating by remember { mutableStateOf(false) }
    val rotation = remember { Animatable(0f) }

    // Infinite transition for continuous rotation
    LaunchedEffect(isRotating) {
        if (isRotating) {
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        } else {
            rotation.snapTo(0f)
        }
    }

    Row(
        modifier = Modifier.padding(end = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    scope.launch {
                        isBalanceVisible = false
                        isRotating = true
                        authViewModel
                            .onCheckBalance()
                            .collect {
                                if (it?.status == true) {
                                    viewBalance = it.walletBalance
                                } else {
                                    showToast(
                                        context,
                                        it?.message ?: "Something Went Wrong"
                                    )
                                }
                                isBalanceVisible = true
                                delay(1.seconds)
                                isRotating = false
                            }

                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .padding(4.dp)
                    .graphicsLayer(rotationZ = rotation.value),
                painter = painterResource(id = R.drawable.ic_refresh),
                contentDescription = "Reset"
            )
        }
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier.padding(4.dp),
                painter = painterResource(id = R.drawable.ic_credit_card),
                contentDescription = "Reset"
            )
            MediumText(
                modifier = Modifier,
                singleLine = true,
                text ="₹${viewBalance.orEmpty()}",
                color = Color.White
            )
        }
    }
}