package com.droid.ffdpboss.home.navigation

import com.droid.ffdpboss.R

sealed class HomeScreen(
    val name: String?=null,
    val route: String,
    val selectedIcon: Int? = null,
    val unSelectedIcon: Int? = null
) {
    data object Home : HomeScreen(
        name="Home",
        route = "home_screen", selectedIcon = R.drawable.ic_home_selected,
        unSelectedIcon = R.drawable.ic_home_selected,
    )

    data object ResultScreen : HomeScreen(
        name="Result",
        route = "result_screen", selectedIcon = R.drawable.ic_result,
        unSelectedIcon = R.drawable.ic_result,
    )

    data object YourBetScreen : HomeScreen(
        name="My Bet",
        route = "your_bet_screen", selectedIcon = R.drawable.ic_trophy,
        unSelectedIcon = R.drawable.ic_trophy,
    )


    data object ContactUsScreen : HomeScreen(
        name="Contact",
        route = "contact_us_screen", selectedIcon = R.drawable.ic_contact,
        unSelectedIcon = R.drawable.ic_contact,
    )

    data object ProfileScreen : HomeScreen(
        name="Profile",
        route = "profile_screen", selectedIcon = R.drawable.ic_profile_selected,
        unSelectedIcon = R.drawable.ic_profile_selected,
    )


    data object ChangePasswordScreen : HomeScreen(route = "forgotpassword_screen")
    data object GameListScreen : HomeScreen(route = "game_list_screen")
    data object GameTypeScreen : HomeScreen(route = "game_type_screen")
    data object GameRulesScreen : HomeScreen(route = "game_rules_screen")
    data object AddBalanceScreen : HomeScreen(route = "add_balance_screen")
    data object AddBetScreen : HomeScreen(route = "add_bet_screen")
    data object TransactionListScreen : HomeScreen(route = "transaction_list_screen")
    data object GameWiseBidResultScreen : HomeScreen(route = "game_wise_bid_result_screen")
    data object GameTypeWiseResultScreenScreen : HomeScreen(
        route = "game_type_wise_result_screen"
    )

    fun withArgs(vararg args: Any?): String {
        val string = buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
        return string
    }

}

