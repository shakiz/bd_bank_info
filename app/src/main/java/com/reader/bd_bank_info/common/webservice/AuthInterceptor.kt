package com.reader.bd_bank_info.common.webservice

import com.example.androidplatter.common.preference.UserPreference
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor private constructor(private val preference: UserPreference) : Interceptor {

    companion object {
        const val AUTHORIZATION = "Authorization"

        @Volatile
        private var instance: AuthInterceptor? = null

        fun getInstance(preference: UserPreference): AuthInterceptor {
            return instance ?: synchronized(AuthInterceptor::class) {
                return@synchronized instance ?: AuthInterceptor(preference).apply {
                    instance = this
                }
            }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        preference.getAccessToken()?.let { accessToken ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
            newRequest.addHeader(AUTHORIZATION, "Bearer $accessToken")

            return chain.proceed(newRequest.build())
        }

        return chain.proceed(chain.request())
    }
}
