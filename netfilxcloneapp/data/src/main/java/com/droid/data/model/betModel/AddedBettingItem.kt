package com.droid.data.model.betModel

import com.droid.data.model.error.ApiMessageModel
import kotlinx.serialization.Serializable

@Serializable
data class BettingDraftList(
    val betDigit: String? = null,
    val betAmount: String? = null,
    val action: String? = null
)

@Serializable
data class AddedBettingItem(val bettingDraftList: List<BettingDraftList>? = null) : ApiMessageModel()
