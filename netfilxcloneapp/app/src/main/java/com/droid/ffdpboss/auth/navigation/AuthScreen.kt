package com.droid.ffdpboss.auth.navigation

sealed class AuthScreen(val route: String) {
    data object SplashScreen : AuthScreen(route = "splash_screen")
    data object LoginScreen : AuthScreen(route = "login_screen")
    data object SignUpScreen : AuthScreen(route = "signup_screen")

}