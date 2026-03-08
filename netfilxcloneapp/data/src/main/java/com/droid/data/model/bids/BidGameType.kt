package com.droid.data.model.bids

import kotlinx.serialization.Serializable

@Serializable
data class BidGameType(
    val id: Int?=null,
    val results: List<GameBidResultitem>?=null,
    val type: String?=null
)