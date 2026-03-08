package com.droid.netfilxclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.droid.netfilxclone.navigation.NetflixNavHost

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Splash screen
        installSplashScreen()
        setContent {
            NetflixApp()
        }
    }
}
@Composable
fun NetflixApp() {
    val navController = rememberNavController()
    NetflixNavHost(
        navController = navController,
        modifier = Modifier.fillMaxSize()
    )
}