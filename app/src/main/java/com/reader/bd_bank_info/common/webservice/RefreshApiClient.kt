package com.reader.bd_bank_info.common.webservice

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RefreshApiClient private constructor(
    baseUrl: String,
    logger: Interceptor? = null,
) : ApiClient {

    companion object {

        @Volatile
        private var instance: RefreshApiClient? = null

        fun getInstance(baseUrl: String, logger: Interceptor? = null): RefreshApiClient {
            return instance ?: synchronized(RefreshApiClient::class) {
                return instance ?: RefreshApiClient(baseUrl, logger).apply { instance = this }
            }
        }
    }

    private val okHttpClient: OkHttpClient by lazy {
        val okHttpBuilder = OkHttpClient.Builder()

        okHttpBuilder.connectTimeout(10, TimeUnit.SECONDS)
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)

        logger?.let { okHttpBuilder.addInterceptor(it) }

        okHttpBuilder.build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    override fun <Service> createService(serviceClass: Class<Service>): Service {
        return retrofit.create(serviceClass)
    }
}
