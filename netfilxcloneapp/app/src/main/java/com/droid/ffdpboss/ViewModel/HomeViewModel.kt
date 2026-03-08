package com.droid.ffdpboss.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droid.data.api.Api
import com.droid.data.model.ContactInfoDTO
import com.droid.data.model.authModel.ApiKeyModel
import com.droid.data.model.balance.AddBalanceRequestBody
import com.droid.data.model.betModel.AddBetModelRequest
import com.droid.data.model.bids.BidGameType
import com.droid.data.model.bids.GameBidGameName
import com.droid.data.model.bids.GameBidResultitem
import com.droid.data.model.bids.GameResultBidType
import com.droid.data.model.bids.toBidType
import com.droid.data.model.bids.toGameResultGameNames
import com.droid.data.model.homeModel.Banner
import com.droid.data.model.homeModel.MasterGame
import com.droid.data.model.homeModel.TransactionListItem
import com.droid.data.model.homeModel.masterGameBajiModel.MasterGameBaji
import com.droid.data.model.homeModel.masterGameBajiModel.gameTypeResponse.MasterGameBajiType
import com.droid.data.model.homeModel.masterGameBajiModel.requestModel.MasterGameRequestBody
import com.droid.data.model.homeModel.masterGameBajiModel.requestModel.MasterGameTypeRequest
import com.droid.data.model.resultModel.GameResultItem
import com.droid.data.model.resultModel.GameType
import com.droid.data.model.rulesModel.GameRules
import com.droid.ffdpboss.data.DataPreferences
import com.droid.ffdpboss.data.model.gameResult.GameResultGameType
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val api: Api,
    private val dataPreferences: DataPreferences
) : ViewModel() {

    val masterGameList = mutableStateListOf<MasterGame>()
    val bannerList = mutableStateListOf<Banner>()
    val gameRulesList = mutableStateListOf<GameRules>()
    var isLoading = mutableStateOf(true)
    var balance = mutableStateOf<String?>("")
    var marqueeText = mutableStateOf<String>("")
    var transactionList = mutableStateListOf<TransactionListItem>()
    val myBids = mutableStateListOf<GameBidGameName>()
    val gameWiseBidResult = mutableStateListOf<GameBidResultitem>()
    val gameTypes = mutableStateListOf<GameResultBidType>()
    val contactInfo = mutableStateOf<ContactInfoDTO?>(null)


    private val allGameTypes = mutableListOf<BidGameType>()
    fun fetchHomeData(apikey: String?) {
        viewModelScope.launch {
            isLoading.value = true
            val homePageResponse = api.homeGameList(ApiKeyModel(apikey.orEmpty()))
            masterGameList.apply {
                clear()
                addAll(homePageResponse?.masterGameList.orEmpty())
            }
            bannerList.apply {
                clear()
                addAll(homePageResponse?.bannerList.orEmpty())
            }
            isLoading.value = false
        }
        fetchHomeMarqueeText()
    }

    fun fetchGameResults() {
        viewModelScope.launch {
            val resultDTO =
                api.getMyBids(ApiKeyModel(apikey = dataPreferences.getApiKey().orEmpty()))
            val games = resultDTO?.masterGameList?.toGameResultGameNames().orEmpty()
            val result =
                myBids.apply {
                    clear()
                    addAll(games)
                }

        }
    }


    fun onAddBalance(addBalanceRequestBody: AddBalanceRequestBody) = callbackFlow {
        val response = api.requestAddBalance(addBalanceRequestBody)
        trySend(response)
        awaitClose {
            close()
        }
    }

    fun onWithDrawralBalance(addBalanceRequestBody: AddBalanceRequestBody) = callbackFlow {
        val response = api.requestWithdrawBalance(addBalanceRequestBody)
        trySend(response)
        awaitClose {
            close()
        }
    }

    val masterBaliList = mutableStateListOf<MasterGameBaji>()
    fun fetchMasterGameBajiData(screenName: String) {
        viewModelScope.launch {
            val apikey = dataPreferences.getApiKey().orEmpty()
            val masterGameRequestBody = MasterGameRequestBody(apikey, screenName)
            val response = api.masterGameBajiList(masterGameRequestBody)
            masterBaliList.apply {
                clear()
                addAll(response?.masterGameBajiList.orEmpty())
            }
            Log.d("#JAPAN", "${masterBaliList}")
        }
    }

    val masterGameTypeList = mutableStateListOf<MasterGameBajiType>()
    fun fetchMasterTypeData(screenName: String, screenId: String) {
        viewModelScope.launch {
            val apikey = dataPreferences.getApiKey().orEmpty()
            val masterGameRequestBody = MasterGameTypeRequest(apikey, screenName, screenId)
            val response = api.masterGameBajiType(masterGameRequestBody)
            masterGameTypeList.apply {
                clear()
                addAll(response?.masterGameBajiTypeList.orEmpty())
            }
            Log.d("#JAPAN", "${masterBaliList}")
        }
    }

    fun fetchGameBettingDraftAddUrl(addBetRequestBody: AddBetModelRequest) = callbackFlow {
        Log.d("#JAPAN--22", "${addBetRequestBody}")
        val response = api.gameBettingDraftAdd(addBetRequestBody)
        trySend(response)
        awaitClose {
            close()
        }
        Log.d("#JAPAN--22", "${response}")
    }

    fun fetchHomeMarqueeText() {
        viewModelScope.launch {
            val apiKey = dataPreferences.getApiKey()
            val response = api.getHomeMarqueeText(ApiKeyModel(apikey = apiKey.orEmpty()))
            marqueeText.value = response?.marqueeText ?: "Unknown"
        }
    }

    fun submitBet(addBetModelRequest: AddBetModelRequest) = callbackFlow {
        val response = api.submitBet(addBetModelRequest = addBetModelRequest)
        trySend(response)
        awaitClose {
            close()
        }
    }

    fun onCheckBalance() {
        viewModelScope.launch {
            val apiKey = dataPreferences.getApiKey()
            val response = api.checkBalance(ApiKeyModel(apiKey.orEmpty()))
            balance.value = response?.walletBalance.orEmpty()
        }
    }

    fun onFetchGameRules() {
        viewModelScope.launch {
            val apiKey = dataPreferences.getApiKey()
            val response = api.getAppGameRules(ApiKeyModel(apiKey.orEmpty()))
            gameRulesList.apply {
                clear()
                addAll(response?.gameRulesList.orEmpty())
            }
        }
    }

    fun fetchTransactionList() {
        viewModelScope.launch {
            val apiKey = dataPreferences.getApiKey().orEmpty()
            val transactionListItem = api.getTransactionList(apikey = ApiKeyModel(apikey = apiKey))
            transactionList.apply {
                clear()
                addAll(transactionListItem?.transactionList.orEmpty())
            }
        }
    }

    fun fetchEachGameBids(gameId: String) {
        viewModelScope.launch {
            val resultDTO =
                api.getMyBids(ApiKeyModel(apikey = dataPreferences.getApiKey().orEmpty()))
            val games = resultDTO?.masterGameList
            val currentGameResult =
                games?.find { it.gameSl == gameId }?.gameTypes
            gameTypes.apply {
                clear()
                addAll(currentGameResult?.toBidType().orEmpty())
            }

            currentGameResult?.forEach {
                allGameTypes.add(it)
            }
            val selectedGameType = gameTypes.find { it.isSelected }?.gameType
            gameWiseBidResult.apply {
                clear()
                addAll(
                    getBidResultForSelectedGameType(
                        gameType = selectedGameType.orEmpty(),
                        allGameTypes = allGameTypes
                    )
                )
            }
        }
    }

    private fun getBidResultForSelectedGameType(
        gameType: String,
        allGameTypes: MutableList<BidGameType>
    ): MutableList<GameBidResultitem> {
        val outPut = allGameTypes.filter { it.type == gameType }
        val gameResultTypeList = mutableListOf<GameBidResultitem>()
        outPut.forEach {
            gameResultTypeList.addAll(it.results.orEmpty())
        }
        return gameResultTypeList
    }

    fun updateGameTypeSelection(gameId: Int) {
        viewModelScope.launch {
            val copyList = gameTypes.toList()
            copyList.forEach {
                it.isSelected = it.gameId == gameId
            }
            gameTypes.apply {
                clear()
                addAll(copyList)
            }
            val selectedGameType = gameTypes.find { it.isSelected }?.gameType
            gameWiseBidResult.apply {
                clear()
                addAll(
                    getBidResultForSelectedGameType(
                        gameType = selectedGameType.orEmpty(),
                        allGameTypes = allGameTypes
                    )
                )
            }
        }
    }

    fun fetchContactInfo() {
        viewModelScope.launch {
            val apikey = dataPreferences.getApiKey().orEmpty()
            val contactInfo = api.getContactInfo(ApiKeyModel(apikey = apikey))
            this@HomeViewModel.contactInfo.value = contactInfo
        }
    }
}