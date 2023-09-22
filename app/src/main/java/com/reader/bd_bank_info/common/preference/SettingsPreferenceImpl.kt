package com.example.androidplatter.common.preference

import android.content.SharedPreferences

class SettingsPreferenceImpl private constructor(
    private val sharedPreferences: SharedPreferences
) : SettingsPreference {

    companion object {
        private var instance: SettingsPreferenceImpl? = null

        fun getInstance(preferences: SharedPreferences): SettingsPreferenceImpl {
            return instance ?: synchronized(SettingsPreferenceImpl::class) {
                instance ?: SettingsPreferenceImpl(preferences).apply { instance = this }
            }
        }
    }
}
