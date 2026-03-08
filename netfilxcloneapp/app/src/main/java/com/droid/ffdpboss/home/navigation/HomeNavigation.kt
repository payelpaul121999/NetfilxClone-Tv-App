package com.droid.ffdpboss.home.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.droid.ffdpboss.balance.AddBalanceScreen
import com.droid.ffdpboss.dashboard.ContactScreen
import com.droid.ffdpboss.dashboard.HomeScreenPage
import com.droid.ffdpboss.dashboard.ChangePasswordScreen
import com.droid.ffdpboss.dashboard.GameResultScreen
import com.droid.ffdpboss.dashboard.GameRulesScreen
import com.droid.ffdpboss.dashboard.ProfileScreen
import com.droid.ffdpboss.dashboard.TransactionListScreen
import com.droid.ffdpboss.dashboard.ResultScreen
import com.droid.ffdpboss.gameScreen.AddBedScreen
import com.droid.ffdpboss.gameScreen.GameListingScreen
import com.droid.ffdpboss.gameScreen.GameTypeScreen
import com.droid.ffdpboss.gameScreen.GameWiseBidScreen
import com.droid.ffdpboss.gameScreen.YourBetScreen

@Composable
fun HomeNavigation(
    navController: NavHostController
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = HomeScreen.Home.route
    ) {
        composable(HomeScreen.Home.route) {
            HomeScreenPage(navController = navController)
        }
        composable(HomeScreen.ChangePasswordScreen.route) {
            ChangePasswordScreen(navController = navController)
        }
        composable(HomeScreen.ResultScreen.route) {
            ResultScreen(navController = navController)
        }
        composable(HomeScreen.ContactUsScreen.route) {
            ContactScreen(navController = navController)
        }
        composable(HomeScreen.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
        composable(HomeScreen.GameRulesScreen.route) {
            GameRulesScreen(navController = navController)
        }
        composable(HomeScreen.TransactionListScreen.route) {
            TransactionListScreen(navController = navController)
        }
        composable(
            route = HomeScreen.GameListScreen.route + "/{gameId}/{gameName}/{screenImageIconUrl}"
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getString("gameId")
            val gameName = backStackEntry.arguments?.getString("gameName")
            val screenImageIconUrl = backStackEntry.arguments?.getString("screenImageIconUrl")
            GameListingScreen(
                navController = navController,
                gameId = gameId.orEmpty(),
                gameName = gameName.orEmpty(),
                screenImageIconUrl = screenImageIconUrl.orEmpty()
            )
        }
        composable(HomeScreen.GameTypeScreen.route + "/{gameId}/{screenId}/{screenName}/{closeHour}") { backStackEntry ->
            val gameId = backStackEntry.arguments?.getString("gameId")
            val screenName = backStackEntry.arguments?.getString("screenName")
            val screenId = backStackEntry.arguments?.getString("screenId")
            val closeHour = backStackEntry.arguments?.getString("closeHour")
            GameTypeScreen(
                navController = navController,
                screenName = screenName ?: "",
                gameId = gameId ?: "",
                screenId = screenId ?: "",
                closeHour = closeHour ?: "",
            )
        }
        composable(HomeScreen.AddBalanceScreen.route + "/{screenName}/{screenType}") { backStackEntry ->
            val screenName = backStackEntry.arguments?.getString("screenName")
            val screenType = backStackEntry.arguments?.getString("screenType")?.toIntOrNull() ?: 0
            AddBalanceScreen(
                navController = navController,
                screenName = screenName.orEmpty(),
                screenType = screenType
            )
        }
        composable(HomeScreen.YourBetScreen.route) {
            YourBetScreen(navController = navController)
        }
        composable(HomeScreen.AddBetScreen.route + "/{gameType}/{gameMasterSl}/{gameMasterBajiSl}/{gameMasterBajiType}/{closeHour}") { backStackEntry ->
            val gameType = backStackEntry.arguments?.getString("gameType")
            val gameMasterSl = backStackEntry.arguments?.getString("gameMasterSl")
            val gameMasterBajiSl = backStackEntry.arguments?.getString("gameMasterBajiSl")
            val gameMasterBajiType = backStackEntry.arguments?.getString("gameMasterBajiType")
            val closeHour = backStackEntry.arguments?.getString("closeHour")
            AddBedScreen(
                navController = navController,
                gameType = gameType ?: "",
                gameMasterSl = gameMasterSl ?: "",
                gameMasterBajiSl = gameMasterBajiSl ?: "",
                gameMasterBajiType = gameMasterBajiType ?: "",
                closeHour = closeHour.orEmpty()
            )
        }

        composable(HomeScreen.GameTypeWiseResultScreenScreen.route + "/{gameType}") { backStackEntry ->
            val gameType = backStackEntry.arguments?.getString("gameType")
            GameResultScreen(gameType = gameType.orEmpty(), navController = navController)
        }

        composable(HomeScreen.GameWiseBidResultScreen.route + "/{gameId}") { backStackEntry ->
            val gameId = backStackEntry.arguments?.getString("gameId")
            GameWiseBidScreen(gameId = gameId.orEmpty(), navController = navController)
        }


    }
}