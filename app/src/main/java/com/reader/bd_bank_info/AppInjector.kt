package com.reader.bd_bank_info

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.androidplatter.common.preference.*
import com.reader.bd_bank_info.common.utils.BASE_URL
import com.reader.bd_bank_info.common.webservice.*
import okhttp3.Interceptor

object AppInjector {

    private fun getSettingsPreference(context: Context): SettingsPreference {
        val prefs =
            context.getSharedPreferences("${context.packageName}_preference_settings", MODE_PRIVATE)
        return SettingsPreferenceImpl.getInstance(prefs)
    }

    private fun getUserPreference(context: Context): UserPreference {
        val prefs =
            context.getSharedPreferences("${context.packageName}_preference_user", MODE_PRIVATE)
        return UserPreferenceImpl.getInstance(prefs)
    }

    fun getAppPreference(context: Context): AppPreference {
        return AppPreferenceImpl.getInstance(
            getUserPreference(context),
            getSettingsPreference(context)
        )
    }

    private fun getHttpLogger(): HttpLogger {
        return HttpLoggerImpl.getInstance()
    }

    private fun getAuthInterceptor(context: Context): Interceptor {
        return AuthInterceptor.getInstance(getUserPreference(context))
    }

    private fun getAppCache(context: Context): AppCache {
        return AppCacheImpl.getInstance(context)
    }

    private fun getRestApiClient(context: Context): ApiClient {
        return RestApiClient.getInstance(
            BASE_URL,
            getAuthInterceptor(context),
            getAppCache(context),
            getHttpLogger().getLoggingInterceptor()
        )
    }
}
