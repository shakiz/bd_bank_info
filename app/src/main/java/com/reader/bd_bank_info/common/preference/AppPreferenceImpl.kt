package com.example.androidplatter.common.preference

class AppPreferenceImpl private constructor(
    private val userPreference: UserPreference,
    private val settingsPreference: SettingsPreference
) : AppPreference {

    companion object {
        private var instance: AppPreferenceImpl? = null

        fun getInstance(userPreference: UserPreference, settingsPreference: SettingsPreference): AppPreferenceImpl {
            return instance ?: synchronized(AppPreferenceImpl::class) {
                instance ?: AppPreferenceImpl(userPreference, settingsPreference).apply { instance = this }
            }
        }
    }

    override fun getUserPreference(): UserPreference {
        return userPreference
    }

    override fun getSettingsPreference(): SettingsPreference {
        return settingsPreference
    }
}
