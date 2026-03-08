package com.droid.ffdpboss.auth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.droid.data.api.Api
import com.droid.data.model.User
import com.droid.data.model.authModel.ApiKeyModel
import com.droid.data.model.authModel.ChangePasswordRequest
import com.droid.data.model.authModel.SignInRequest
import com.droid.data.model.authModel.SignUpRequest
import com.droid.data.model.authModel.toUser
import com.droid.data.model.serializeUser
import com.droid.ffdpboss.data.DataPreferences
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class AuthViewModel(
    private val api: Api,
    private val dataPreferences: DataPreferences
) : ViewModel() {

    val user = mutableStateOf<User?>(null)

    fun fetchUserData() {
        try {
            val userData = dataPreferences.getUser()
            user.value = userData
        }catch (e : Exception){
            user.value = null
        }

    }

    fun onLogin(mobileNumber: String, password: String) = callbackFlow {
        val signInResponse = api.signIn(
            SignInRequest(
                mobileNumber, password
            )
        )
        val user = signInResponse?.toUser()
        val serializedUser = serializeUser(user)
        dataPreferences.addUserData(serializedUser)
        dataPreferences.saveApiKey(signInResponse?.apiKey.orEmpty())
        Log.e("##JAPAN", "${signInResponse}")
        trySend(signInResponse)
        awaitClose {
            close()
        }
    }

    fun onRegister(
        fullName: String,
        mobileNumber: String,
        emailId: String,
        password: String,
        conPassword: String
    ) = callbackFlow {
        val signUpResponse = api.signUp(
            SignUpRequest(emailId, fullName, mobileNumber, conPassword, password)
        )
        val user = signUpResponse?.toUser()
        val serializedUser = serializeUser(user)
        dataPreferences.addUserData(serializedUser)
        dataPreferences.saveApiKey(signUpResponse?.apiKey.orEmpty())
        trySend(signUpResponse)
        awaitClose {
            close()
        }
    }


    fun onLogOut() = callbackFlow {
        dataPreferences.clearAll()
        trySend(true)
        awaitClose {
            close()
        }
    }

    fun onCheckApikeyValidate(apikey: String?) = callbackFlow {
        val response = api.checkValidityOfApikey(ApiKeyModel(apikey.orEmpty()))
        trySend(response)
        awaitClose {
            close()
        }
    }
    fun onCheckBalance() = callbackFlow {
        val apiKey = dataPreferences.getApiKey()
        val response = api.checkBalance(ApiKeyModel(apiKey.orEmpty()))
        Log.i("JAPAN", "onCheckBalance: ${response}")
        trySend(response)
        awaitClose {
            close()
        }
    }
    fun onChangePassword(changePassword:ChangePasswordRequest) = callbackFlow {
        val response = api.getChangePasswordRequest(changePassword)
        trySend(response)
        awaitClose {
            close()
        }
    }
}