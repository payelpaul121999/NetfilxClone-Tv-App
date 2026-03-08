package com.droid.data.model.homeModel


import com.droid.data.model.error.ApiMessageModel
import kotlinx.serialization.Serializable

@Serializable
data class HomeGameWithBannerModel(
    val bannerList: List<Banner>,
    val masterGameList: List<MasterGame>,
): ApiMessageModel()