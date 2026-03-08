package com.droid.ffdpboss.auth.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.droid.ffdpboss.auth.LoginScreen
import com.droid.ffdpboss.auth.RegisterScreen
import com.droid.ffdpboss.dashboard.SplashScreen

@Composable
fun AuthNavigation(
    navController: NavHostController
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = AuthScreen.LoginScreen.route
    ) {
        composable(AuthScreen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(AuthScreen.SignUpScreen.route) {
            RegisterScreen(navController = navController)
        }
    }
}