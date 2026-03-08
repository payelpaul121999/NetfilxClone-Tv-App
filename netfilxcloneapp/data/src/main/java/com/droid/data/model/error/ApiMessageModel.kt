package com.droid.data.model.error

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class ApiMessageModel(
    @SerialName("Status")
    val Status : Boolean?=null,
    @SerialName("Message")
    val Message : String?=null
)
