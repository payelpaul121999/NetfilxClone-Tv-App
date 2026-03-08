package com.droid.data.model.bids

import kotlinx.serialization.Serializable

@Serializable
data class GameBidResultitem(
    val bajiName: String?=null,
    val betAmount: String?=null,
    val betdate: String?=null,
    val digitNo: String?=null,
    val winStat: String?=null
)