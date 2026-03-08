package com.droid.ffdpboss.dashboard

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.droid.ffdpboss.R
import com.droid.ffdpboss.auth.AuthActivity
import com.droid.ffdpboss.data.DataPreferences
import com.droid.ffdpboss.auth.AuthViewModel
import com.droid.ffdpboss.home.HomeActivity
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        delay(2000) // Simulate a loading period
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.app_icon),
            contentScale = ContentScale.Fit,
            contentDescription = "description"
        )
    }

}

