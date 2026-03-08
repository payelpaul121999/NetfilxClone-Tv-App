package com.droid.data.api

import android.util.Log
import com.droid.data.appGameResultUrl
import com.droid.data.appGameRulesUrl
import com.droid.data.changePasswordUrl
import com.droid.data.checkApikeyValidate
import com.droid.data.checkBalanceUrl
import com.droid.data.contactInfoUrl
import com.droid.data.depositMoneyUrl
import com.droid.data.gameBettingDraftAddUrl
import com.droid.data.gameBettingDraftListUrl
import com.droid.data.gameMasterBajiTypeUrl
import com.droid.data.gameMasterBajiUrl
import com.droid.data.generateUrl
import com.droid.data.homePageUrl
import com.droid.data.logout
import com.droid.data.marqueeTextUrl
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
import com.droid.data.myBidUrl
import com.droid.data.signUpUrl
import com.droid.data.submitBetUrl
import com.droid.data.transactionListUrl
import com.droid.data.withdrawMoneyUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

class ApiImpl(
    private val client: HttpClient
) : Api {
    override suspend fun signIn(signInRequest: SignInRequest): SignInResponse? {
        val apiUrl = generateUrl("authentication/signin.php ")

        return try {
            val response = client.post<String?> {
                this.url(apiUrl)
                contentType(ContentType.Application.Json)
                body = signInRequest
            }

            Json { ignoreUnknownKeys = true }.decodeFromString<SignInResponse?>(
                response!!
            )
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch (e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): RegisterResponse? {
        val apiUrl = generateUrl(signUpUrl)

        return try {
            val response = client.post<String?> {
                this.url(apiUrl)
                contentType(ContentType.Application.Json)
                body = signUpRequest
            }
            Json { ignoreUnknownKeys = true }.decodeFromString<RegisterResponse?>(
                response.orEmpty()
            )
        } catch (e: Exception) {
            println("JAPAN: ${e}")
            null
        }
    }

    override suspend fun checkBalance(apiKey: ApiKeyModel): CheckBalanceModel? {
        val apiUrl = generateUrl(checkBalanceUrl)

        return try {
            val response = client.post<String?> {
                this.url(apiUrl)
                contentType(ContentType.Application.Json)
                body = apiKey
            }
            Json { ignoreUnknownKeys = true }.decodeFromString<CheckBalanceModel?>(
                response!!
            )
        } catch (e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }

    override suspend fun homeGameList(apikey: ApiKeyModel): HomeGameWithBannerModel? {
        val apiUrl = generateUrl(homePageUrl)
        val client = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(Json { ignoreUnknownKeys = true })
            }
            install(Logging) {
                level = LogLevel.BODY
            }
        }
        return try {
            val response: HttpResponse = client.post(apiUrl) {
                contentType(ContentType.Application.Json)
                body = apikey
            }
            val responseBody: String = response.receive()
            Json {
                ignoreUnknownKeys = true
            }.decodeFromString<HomeGameWithBannerModel>(responseBody)
        } catch (e: RedirectResponseException) {
            println("Redirect error: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.Unauthorized) {
                println("Access denied: Unauthorized (401)")
            } else if (e.response.status == HttpStatusCode.Forbidden) {
                println("Access denied: Forbidden (403)")
            }
            null
        } catch (e: ServerResponseException) {
            println("Server error: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            println("General error: ${e.message}")
            null
        } finally {
            client.close()
        }
    }

    override suspend fun logOut(apikey: ApiKeyModel): ApiMessageModel? {
        val apiUrl = generateUrl(logout)
        val client = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(Json { ignoreUnknownKeys = true })
            }
            install(Logging) {
                level = LogLevel.BODY
            }
        }
        return try {
            val response: HttpResponse = client.post(apiUrl) {
                contentType(ContentType.Application.Json)
                body = apikey
            }
            val responseBody: String = response.receive()
            Json { ignoreUnknownKeys = true }.decodeFromString<ApiMessageModel>(responseBody)
        } catch (e: RedirectResponseException) {
            println("Redirect error: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.Unauthorized) {
                println("Access denied: Unauthorized (401)")
            } else if (e.response.status == HttpStatusCode.Forbidden) {
                println("Access denied: Forbidden (403)")
            }
            null
        } catch (e: ServerResponseException) {
            println("Server error: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            println("General error: ${e.message}")
            null
        } finally {
            client.close()
        }
    }

    override suspend fun checkValidityOfApikey(apikey: ApiKeyModel): ApiMessageModel? {
        val apiUrl = generateUrl(checkApikeyValidate)
        val client = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(Json { ignoreUnknownKeys = true })
            }
            install(Logging) {
                level = LogLevel.BODY
            }
        }
        return try {
            val response: HttpResponse = client.post(apiUrl) {
                contentType(ContentType.Application.Json)
                body = apikey
            }
            val responseBody: String = response.receive()
            Json { ignoreUnknownKeys = true }.decodeFromString<ApiMessageModel>(responseBody)
        } catch (e: RedirectResponseException) {
            println("Redirect error: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.Unauthorized) {
                println("Access denied: Unauthorized (401)")
            } else if (e.response.status == HttpStatusCode.Forbidden) {
                println("Access denied: Forbidden (403)")
            }
            null
        } catch (e: ServerResponseException) {
            println("Server error: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            println("General error: ${e.message}")
            null
        } finally {
            client.close()
        }
    }

    override suspend fun requestAddBalance(addBalanceRequestBody: AddBalanceRequestBody): ApiMessageModel? {
        val apiUrl = generateUrl(depositMoneyUrl)
        val client = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(Json { ignoreUnknownKeys = true })
            }
            install(Logging) {
                level = LogLevel.BODY
            }
        }
        return try {
            val response: HttpResponse = client.post(apiUrl) {
                contentType(ContentType.Application.Json)
                body = addBalanceRequestBody
            }
            val responseBody: String = response.receive()
            Json { ignoreUnknownKeys = true }.decodeFromString<ApiMessageModel>(responseBody)
        } catch (e: RedirectResponseException) {
            println("Redirect error: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.Unauthorized) {
                println("Access denied: Unauthorized (401)")
            } else if (e.response.status == HttpStatusCode.Forbidden) {
                println("Access denied: Forbidden (403)")
            }
            null
        } catch (e: ServerResponseException) {
            println("Server error: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            println("General error: ${e.message}")
            null
        } finally {
            client.close()
        }
    }

    override suspend fun requestWithdrawBalance(addBalanceRequestBody: AddBalanceRequestBody): ApiMessageModel? {
        val apiUrl = generateUrl(withdrawMoneyUrl)
        val client = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(Json { ignoreUnknownKeys = true })
            }
            install(Logging) {
                level = LogLevel.BODY
            }
        }
        return try {
            val response: HttpResponse = client.post(apiUrl) {
                contentType(ContentType.Application.Json)
                body = addBalanceRequestBody
            }
            val responseBody: String = response.receive()
            Json { ignoreUnknownKeys = true }.decodeFromString<ApiMessageModel>(responseBody)
        } catch (e: RedirectResponseException) {
            println("Redirect error: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.Unauthorized) {
                println("Access denied: Unauthorized (401)")
            } else if (e.response.status == HttpStatusCode.Forbidden) {
                println("Access denied: Forbidden (403)")
            }
            null
        } catch (e: ServerResponseException) {
            println("Server error: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            println("General error: ${e.message}")
            null
        } finally {
            client.close()
        }
    }

    override suspend fun masterGameBajiList(masterGameRequestBody: MasterGameRequestBody): MasterGameBajiResponseModel? {
        val apiUrl = generateUrl(gameMasterBajiUrl)
        val client = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(Json { ignoreUnknownKeys = true })
            }
            install(Logging) {
                level = LogLevel.BODY
            }
        }
        return try {
            val response: HttpResponse = client.post(apiUrl) {
                contentType(ContentType.Application.Json)
                body = masterGameRequestBody
            }
            val responseBody: String = response.receive()
            Json { ignoreUnknownKeys = true }.decodeFromString<MasterGameBajiResponseModel>(
                responseBody
            )
        } catch (e: RedirectResponseException) {
            Log.d("###JAPAN", "Redirect error: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.Unauthorized) {
                Log.d("###JAPAN", "Access denied: Unauthorized (401)")
            } else if (e.response.status == HttpStatusCode.Forbidden) {
                Log.d("###JAPAN", "Access denied: Forbidden (403)")
            }
            null
        } catch (e: ServerResponseException) {
            Log.d("###JAPAN", "Server error: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            Log.d("###JAPAN", "General error: ${e.message}")
            null
        } finally {
            client.close()
        }
    }

    override suspend fun masterGameBajiType(masterGameTypeRequest: MasterGameTypeRequest): masterGameBajiTypeResponse? {
        val apiUrl = generateUrl(gameMasterBajiTypeUrl)
        val client = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(Json { ignoreUnknownKeys = true })
            }
            install(Logging) {
                level = LogLevel.BODY
            }
        }
        return try {
            val response: HttpResponse = client.post(apiUrl) {
                contentType(ContentType.Application.Json)
                body = masterGameTypeRequest
            }
            val responseBody: String = response.receive()
            Json { ignoreUnknownKeys = true }.decodeFromString<masterGameBajiTypeResponse>(
                responseBody
            )
        } catch (e: RedirectResponseException) {
            Log.d("###JAPAN", "Redirect error: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.Unauthorized) {
                Log.d("###JAPAN", "Access denied: Unauthorized (401)")
            } else if (e.response.status == HttpStatusCode.Forbidden) {
                Log.d("###JAPAN", "Access denied: Forbidden (403)")
            }
            null
        } catch (e: ServerResponseException) {
            Log.d("###JAPAN", "Server error: ${e.response.status.description}")
            null
        } catch (e: Exception) {
            Log.d("###JAPAN", "General error: ${e.message}")
            null
        } finally {
            client.close()
        }
    }

    override suspend fun gameBettingDraftAdd(addBetModelRequest: AddBetModelRequest): ApiMessageModel? {
        val apiUrl = generateUrl(gameBettingDraftAddUrl)
        return try {
            val response: HttpResponse = client.post(apiUrl) {
                contentType(ContentType.Application.Json)
                body = addBetModelRequest
            }
            val responseBody: String = response.receive()
            Json { ignoreUnknownKeys = true }.decodeFromString<ApiMessageModel>(responseBody)
        } catch (e: Exception) {
            println("General error: ${e.message}")
            null
        }
    }

    override suspend fun getGameBettingDraftList(addBetModelRequest: AddBetModelRequest): AddedBettingItem? {
        val apiUrl = generateUrl(gameBettingDraftListUrl)
        return try {
            val response: HttpResponse = client.post(apiUrl) {
                contentType(ContentType.Application.Json)
                body = addBetModelRequest
            }
            val responseBody: String = response.receive()
            Json { ignoreUnknownKeys = true }.decodeFromString<AddedBettingItem>(responseBody)
        } catch (e: Exception) {
            println("General error: ${e.message}")
            null
        }
    }

    override suspend fun submitBet(addBetModelRequest: AddBetModelRequest): ApiMessageModel? {
        val apiUrl = generateUrl(submitBetUrl)
        return try {
            val response: HttpResponse = client.post(apiUrl) {
                contentType(ContentType.Application.Json)
                body = addBetModelRequest
            }
            val responseBody: String = response.receive()
            Json { ignoreUnknownKeys = true }.decodeFromString<ApiMessageModel>(responseBody)
        } catch (e: Exception) {
            println("General error: ${e.message}")
            null
        }
    }

    override suspend fun getChangePasswordRequest(changePassword: ChangePasswordRequest): ApiMessageModel? {
        val apiUrl = generateUrl(changePasswordUrl)
        return try {
            val response: HttpResponse = client.post(apiUrl) {
                contentType(ContentType.Application.Json)
                body = changePassword
            }
            val responseBody: String = response.receive()
            Json { ignoreUnknownKeys = true }.decodeFromString<ApiMessageModel>(responseBody)
        } catch (e: Exception) {
            println("General error: ${e.message}")
            null
        }
    }

    override suspend fun getAppGameRules(apikey: ApiKeyModel): GameRulesModelResponse? {
        val apiUrl = generateUrl(appGameRulesUrl)
        return try {
            val response: HttpResponse = client.post(apiUrl) {
                contentType(ContentType.Application.Json)
                body = apikey
            }
            val responseBody: String = response.receive()
            Json { ignoreUnknownKeys = true }.decodeFromString<GameRulesModelResponse>(responseBody)
        } catch (e: Exception) {
            println("General error: ${e.message}")
            null
        }
    }

    override suspend fun getGameResults(apikey: ApiKeyModel): GameResultDTO? {
        val apiUrl = generateUrl(appGameResultUrl)
        return try {
            val response: HttpResponse = client.post(apiUrl) {
                contentType(ContentType.Application.Json)
                body = apikey
            }
            val responseBody: String = response.receive()
            Json { ignoreUnknownKeys = true }.decodeFromString<GameResultDTO>(responseBody)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getMyBids(apikey: ApiKeyModel): MyBidListDTO? {
        val apiUrl = generateUrl(myBidUrl)
        return try {
            val response: HttpResponse = client.post(apiUrl) {
                contentType(ContentType.Application.Json)
                body = apikey
            }
            val responseBody: String = response.receive()
            Json { ignoreUnknownKeys = true }.decodeFromString<MyBidListDTO>(responseBody)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getHomeMarqueeText(apikey: ApiKeyModel): MarqueeText? {
        val apiUrl = generateUrl(marqueeTextUrl)
        return try {
            val response: HttpResponse = client.post(apiUrl) {
                contentType(ContentType.Application.Json)
                body = apikey
            }
            val responseBody: String = response.receive()
            Json { ignoreUnknownKeys = true }.decodeFromString<MarqueeText>(responseBody)
        } catch (e: Exception) {
            println("General error: ${e.message}")
            null
        }
    }

    override suspend fun getTransactionList(apikey: ApiKeyModel): TransactionList? {
        val apiUrl = generateUrl(transactionListUrl)
        return try {
            val response: HttpResponse = client.post(apiUrl) {
                contentType(ContentType.Application.Json)
                body = apikey
            }
            val responseBody: String = response.receive()
            Json { ignoreUnknownKeys = true }.decodeFromString<TransactionList>(responseBody)
        } catch (e: Exception) {
            println("General error: ${e.message}")
            null
        }
    }

    override suspend fun getContactInfo(apikey: ApiKeyModel): ContactInfoDTO? {
        val apiUrl = generateUrl(contactInfoUrl)
        return try {
            val response: HttpResponse = client.post(apiUrl) {
                contentType(ContentType.Application.Json)
                body = apikey
            }
            val responseBody: String = response.receive()
            Json { ignoreUnknownKeys = true }.decodeFromString<ContactInfoDTO>(responseBody)
        } catch (e: Exception) {
            println("General error: ${e.message}")
            null
        }
    }
}