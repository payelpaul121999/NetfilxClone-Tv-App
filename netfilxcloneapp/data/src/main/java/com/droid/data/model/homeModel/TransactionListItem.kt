package com.droid.data.model.homeModel

import com.droid.data.model.error.ApiMessageModel
import kotlinx.serialization.Serializable

@Serializable
data class TransactionListItem(
    val acType: String? = null,
    val amount: String? = null,
    val payDate: String? = null,
    val paymentMode: String? = null,
    val remark: String? = null
)

@Serializable
data class TransactionList(
    val transactionList: List<TransactionListItem>
) : ApiMessageModel()