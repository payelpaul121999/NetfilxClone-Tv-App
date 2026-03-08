package com.droid.data.model.bids

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MasterGame(
    val gameName: String?=null,
    val gamePhotoPath: String?=null,
    val gameSl: String?=null,
    val gameTitle: String?=null,
    val gameTypes: List<BidGameType>?=null
)