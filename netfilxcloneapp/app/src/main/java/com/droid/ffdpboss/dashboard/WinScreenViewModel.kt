package com.droid.ffdpboss.dashboard

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droid.data.api.Api
import com.droid.data.model.authModel.ApiKeyModel
import com.droid.data.model.resultModel.GameResultItem
import com.droid.ffdpboss.data.DataPreferences
import com.droid.ffdpboss.data.model.gameResult.GameResultGameName
import com.droid.ffdpboss.data.model.gameResult.GameResultGameType
import com.droid.ffdpboss.data.model.gameResult.toGameResultGameNames
import com.droid.ffdpboss.data.model.gameResult.toGameResultGameTypes
import kotlinx.coroutines.launch

class WinScreenViewModel(
    private val api: Api,
    private val dataPreferences: DataPreferences
) : ViewModel() {
    val allGameResultsByGameType = mutableStateListOf<GameResultGameName>()
    val gameTypes = mutableStateListOf<GameResultGameType>()
    val gameResultsBasedOnGameTypes = mutableStateListOf<List<GameResultItem>>()


    fun fetchGameResults() {
        viewModelScope.launch {
            val resultDTO = api.getGameResults(ApiKeyModel(apikey = dataPreferences.getApiKey().orEmpty()))

            val games = resultDTO?.masterGameList?.toGameResultGameNames().orEmpty()
            allGameResultsByGameType.apply {
                clear()
                addAll(games)
            }
        }
    }


    fun fetchEachGameResults(gameId: String) {
        viewModelScope.launch {
            val resultDTO =
                api.getGameResults(ApiKeyModel(apikey = dataPreferences.getApiKey().orEmpty()))
            val games = resultDTO?.masterGameList
            val currentGameResult =
                games?.find { it.gameSl == gameId }?.gameTypes?.toGameResultGameTypes().orEmpty()
            gameTypes.apply {
                clear()
                addAll(currentGameResult)
            }
            gameResultsBasedOnGameTypes.apply {
                clear()
                addAll((groupResultsByBetDate(currentGameResult.firstOrNull()?.results.orEmpty())))
            }
        }
    }


    fun groupResultsByBetDate(results: List<GameResultItem>): List<List<GameResultItem>> {
        return results.groupBy { it.betdate }.values.toList()
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
            val currentGameResult = copyList.find { it.isSelected }?.results.orEmpty()
            gameResultsBasedOnGameTypes.apply {
                clear()
                addAll((groupResultsByBetDate(currentGameResult)))
            }
        }
    }

}