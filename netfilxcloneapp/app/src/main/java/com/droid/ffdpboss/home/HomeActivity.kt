package com.droid.ffdpboss.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.droid.ffdpboss.auth.navigation.AuthNavigation
import com.droid.ffdpboss.home.navigation.HomeNavigation
import com.droid.ffdpboss.ui.theme.FFDpbossTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FFDpbossTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    // A surface container using the 'background' color from the theme
                    HomeNavigation(navController)
                }
            }
        }
    }
}

