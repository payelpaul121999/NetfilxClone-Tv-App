package com.droid.ffdpboss

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.droid.ffdpboss.auth.AuthActivity
import com.droid.ffdpboss.data.DataPreferences
import com.droid.ffdpboss.home.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val dataPreferences: DataPreferences by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // val splashScreen = installSplashScreen()
        setContent {
            LaunchedEffect(Unit) {
                delay(2000)
                withContext(Dispatchers.Main) {
                    val localAPiToken = dataPreferences.getApiKey()
                    finish()
                    if (localAPiToken != null) {
                        startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                    } else {
                        startActivity(Intent(this@MainActivity, AuthActivity::class.java))
                    }
                }
            }
            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) {
                var offsetState by remember { mutableStateOf(0f) }
                val animatedOffset by animateFloatAsState(
                    targetValue = offsetState,
                    animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
                )

                LaunchedEffect(Unit) {
                    while (true) {
                        offsetState = 20f
                        delay(1000)
                        offsetState = -20f
                        delay(1000)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .offset(y = animatedOffset.dp)
                            .clip(CircleShape),
                        painter = painterResource(id = R.drawable.app_icon),
                        contentScale = ContentScale.Fit,
                        contentDescription = "description"
                    )
                }
            }

        }
    }
}
