package com.droid.data.model.balance

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddBalanceRequestBody(
    @SerialName("apikey")
    val apikey: String?,
    @SerialName("mobile_no")
    val mobileNo: String?,
    @SerialName("payment_method")
    val paymentMethod: String?,
    @SerialName("request_amount")
    val requestAmount: String?
)
