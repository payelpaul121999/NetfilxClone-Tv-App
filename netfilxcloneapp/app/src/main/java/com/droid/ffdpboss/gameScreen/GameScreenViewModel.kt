package com.droid.ffdpboss.gameScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droid.data.api.Api
import com.droid.data.model.betModel.AddBetModelRequest
import com.droid.data.model.betModel.BettingDraftList
import kotlinx.coroutines.launch

class GameScreenViewModel(
    private val api: Api
) : ViewModel() {

    val betList = mutableStateListOf<BettingDraftList>()


    fun fetchGameAddedBettingList(addBetModelRequest: AddBetModelRequest) {
        viewModelScope.launch {
            val response = api.getGameBettingDraftList(addBetModelRequest)
            betList.apply {
                clear()
                addAll(response?.bettingDraftList.orEmpty())
            }
        }
    }

}
