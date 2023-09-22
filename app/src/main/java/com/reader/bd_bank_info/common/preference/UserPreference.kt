package com.example.androidplatter.common.preference

interface UserPreference {
    companion object {
        const val KEY_EMAIL = "email"
        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_REFRESH_TOKEN = "refresh_token"
    }

    fun clearUserPreferences()

    fun saveEmail(email: String? = null)

    fun getEmail(): String?

    fun saveAccessToken(token: String? = null)

    fun getAccessToken(): String?

    fun saveRefreshToken(token: String? = null)

    fun getRefreshToken(): String?
}
