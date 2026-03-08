package com.droid.data.model

import com.droid.data.model.error.ApiMessageModel
import kotlinx.serialization.Serializable

@Serializable
data class ContactInfoDTO(
    val mobileNo: String? = null,
    val whatsAppNo: String? = null
) : ApiMessageModel()
