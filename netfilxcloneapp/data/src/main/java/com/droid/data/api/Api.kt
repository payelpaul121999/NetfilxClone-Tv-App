package com.droid.data.api

import com.droid.data.model.CheckBalanceModel
import com.droid.data.model.ContactInfoDTO
import com.droid.data.model.authModel.ApiKeyModel
import com.droid.data.model.authModel.ChangePasswordRequest
import com.droid.data.model.authModel.RegisterResponse
import com.droid.data.model.authModel.SignInRequest
import com.droid.data.model.authModel.SignInResponse
import com.droid.data.model.authModel.SignUpRequest
import com.droid.data.model.balance.AddBalanceRequestBody
import com.droid.data.model.betModel.AddBetModelRequest
import com.droid.data.model.betModel.AddedBettingItem
import com.droid.data.model.bids.MyBidListDTO
import com.droid.data.model.error.ApiMessageModel
import com.droid.data.model.homeModel.HomeGameWithBannerModel
import com.droid.data.model.homeModel.MarqueeText
import com.droid.data.model.homeModel.TransactionList
import com.droid.data.model.homeModel.masterGameBajiModel.MasterGameBajiResponseModel
import com.droid.data.model.homeModel.masterGameBajiModel.gameTypeResponse.masterGameBajiTypeResponse
import com.droid.data.model.homeModel.masterGameBajiModel.requestModel.MasterGameRequestBody
import com.droid.data.model.homeModel.masterGameBajiModel.requestModel.MasterGameTypeRequest
import com.droid.data.model.resultModel.GameResultDTO
import com.droid.data.model.rulesModel.GameRulesModelResponse


interface Api {
    suspend fun signIn(signInRequest: SignInRequest) : SignInResponse?
    suspend fun signUp(signUpRequest: SignUpRequest): RegisterResponse?

    suspend fun checkBalance(apikey: ApiKeyModel) : CheckBalanceModel?

    suspend fun homeGameList(apikey: ApiKeyModel): HomeGameWithBannerModel?
    suspend fun logOut(apikey: ApiKeyModel): ApiMessageModel?
    suspend fun checkValidityOfApikey(apikey: ApiKeyModel): ApiMessageModel?

    suspend fun requestAddBalance(addBalanceRequestBody: AddBalanceRequestBody) :ApiMessageModel?
    suspend fun requestWithdrawBalance(addBalanceRequestBody: AddBalanceRequestBody) :ApiMessageModel?

    suspend fun masterGameBajiList(masterGameRequestBody: MasterGameRequestBody): MasterGameBajiResponseModel?

    suspend fun masterGameBajiType(masterGameTypeRequest: MasterGameTypeRequest) : masterGameBajiTypeResponse?

    suspend fun gameBettingDraftAdd(addBetModelRequest: AddBetModelRequest) :ApiMessageModel?
    suspend fun getGameBettingDraftList(addBetModelRequest: AddBetModelRequest) :AddedBettingItem?
    suspend fun submitBet(addBetModelRequest: AddBetModelRequest):ApiMessageModel?
    suspend fun getChangePasswordRequest(changePassword:ChangePasswordRequest):ApiMessageModel?

    suspend fun getAppGameRules(apikey: ApiKeyModel):GameRulesModelResponse?
    suspend fun getMyBids(apikey: ApiKeyModel): MyBidListDTO?
    suspend fun getGameResults(apikey: ApiKeyModel): GameResultDTO?
    suspend fun getHomeMarqueeText(apikey: ApiKeyModel): MarqueeText?
    suspend fun getTransactionList(apikey: ApiKeyModel): TransactionList?
    suspend fun getContactInfo(apikey: ApiKeyModel): ContactInfoDTO?
}