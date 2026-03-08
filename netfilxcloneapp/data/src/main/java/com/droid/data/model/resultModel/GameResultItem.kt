package com.droid.data.model.resultModel

import kotlinx.serialization.Serializable

@Serializable
data class GameResultItem(
    val betdate: String?=null,
    val pattiWinDigitNo: String?=null,
    val singleWinDigitNo: String?=null
)