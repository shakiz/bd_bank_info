package com.example.androidplatter.common.preference

import android.content.SharedPreferences
import androidx.core.content.edit

class UserPreferenceImpl private constructor(
    private val sharedPreferences: SharedPreferences
) : UserPreference {

    companion object {
        private var instance: UserPreferenceImpl? = null

        fun getInstance(preferences: SharedPreferences): UserPreferenceImpl {
            return instance ?: synchronized(UserPreferenceImpl::class) {
                instance ?: UserPreferenceImpl(preferences).apply { instance = this }
            }
        }
    }

    override fun clearUserPreferences() {
        sharedPreferences.edit { clear() }
    }

    override fun saveEmail(email: String?) {
        sharedPreferences.edit { putString(UserPreference.KEY_EMAIL, email) }
    }

    override fun getEmail(): String? {
        return sharedPreferences.getString(UserPreference.KEY_EMAIL, null)
    }

    override fun saveAccessToken(token: String?) {
        sharedPreferences.edit { putString(UserPreference.KEY_ACCESS_TOKEN, token) }
    }

    override fun getAccessToken(): String? {
        return sharedPreferences.getString(UserPreference.KEY_ACCESS_TOKEN, null)
    }

    override fun saveRefreshToken(token: String?) {
        sharedPreferences.edit { putString(UserPreference.KEY_REFRESH_TOKEN, token) }
    }

    override fun getRefreshToken(): String? {
        return sharedPreferences.getString(UserPreference.KEY_REFRESH_TOKEN, null)
    }
}
