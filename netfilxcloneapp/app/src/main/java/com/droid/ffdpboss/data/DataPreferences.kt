package com.droid.ffdpboss.data

import android.content.Context
import android.content.SharedPreferences
import com.droid.data.model.User
import com.droid.data.model.deserializeUser

class DataPreferences(private val myContext: Context) {

    private val pref: SharedPreferences
    private val editor: SharedPreferences.Editor
    private val PRIVATE_MODE = 0
    fun setMessage(flag: Boolean) {
        editor.putBoolean(HOME_ENABLE, flag)
        editor.apply()
    }

    fun getMessage(): Boolean {
        return pref.getBoolean(HOME_ENABLE, false)
    }

    fun getApiKey(): String? {
        return pref.getString(API_KEY, null)
    }

    fun addUserData(userData: String) {
        editor.putString(USER_DATA, userData)
        editor.apply()
    }

    fun getUser(): User? {
        val userDataString = pref.getString(USER_DATA, null)
        val user = deserializeUser(userDataString.orEmpty())
        return user
    }

    fun saveApiKey(str: String) {
        editor.putString(API_KEY, str)
        editor.apply()
    }

    fun clearAll() {
        editor.clear()
        editor.apply()
    }

    companion object {
        private const val HOME_ENABLE = "HOME_ENABLE"
        private const val PREF_NAME = "PREF_NAME"
        private const val API_KEY = "API_KEY"
        private const val USER_DATA = "USER_DATA"

    }

    init {
        pref = myContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }
}