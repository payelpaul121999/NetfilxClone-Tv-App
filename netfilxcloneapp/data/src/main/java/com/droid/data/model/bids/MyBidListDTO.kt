package com.droid.data.model.bids

import com.droid.data.model.error.ApiMessageModel
import kotlinx.serialization.Serializable

@Serializable
data class MyBidListDTO(
    val masterGameList: List<MasterGame>?=null
): ApiMessageModel()