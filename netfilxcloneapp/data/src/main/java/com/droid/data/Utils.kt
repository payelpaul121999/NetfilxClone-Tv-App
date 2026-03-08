package com.droid.data

import android.content.Context
import android.widget.Toast

fun generateUrl(endpoint: String): String {
    val baseUrl = "https://trialforyou.co.in/trial/kolkataff98/api" // Replace this with your base URL
    return "$baseUrl/$endpoint"
}
val signUpUrl ="authentication/signup.php"
val checkBalanceUrl ="profile/wallet-balance.php"
val homePageUrl ="home/banner-with-game.php"
val logout ="authentication/logout.php"
val checkApikeyValidate ="authentication/api-key-checkup.php"
val depositMoneyUrl="account/deposit-money-add.php"
val withdrawMoneyUrl="account/withdraw-money-add.php"
val gameMasterBajiUrl="betting/game-master-baji.php"
val gameMasterBajiTypeUrl="betting/game-master-baji-type.php"
val gameBettingDraftAddUrl="betting/game-betting-draft-add.php"
val gameBettingDraftListUrl="betting/game-betting-draft-list.php"
val submitBetUrl="betting/game-betting-add.php"
val changePasswordUrl="authentication/password-change.php"
val appGameRulesUrl="game/game-rules.php"
val appGameResultUrl="game/game-result.php"
val marqueeTextUrl="home/marquee-text.php"
val transactionListUrl="account/transaction-list.php"
val myBidUrl="game/my-bid.php"
val contactInfoUrl="profile/contact.php"
fun isValidMobileNumber(mobileNumber: String): Boolean {
    val regex = "^[7-9][0-9]{9}$"
    return mobileNumber.matches(regex.toRegex())
}
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
fun isValidAmount(amount: String): Boolean {
    val regex = Regex("^\\d{2,5}$")
    return amount.matches(regex)
}