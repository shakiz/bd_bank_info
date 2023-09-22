package com.reader.bd_bank_info.common.webservice

import okhttp3.Authenticator
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestApiClient private constructor(
    baseUrl: String,
    authInterceptor: Interceptor,
    cache: Cache? = null,
    logger: Interceptor? = null
) : ApiClient {

    companion object {

        @Volatile
        private var instance: RestApiClient? = null

        fun getInstance(
            baseUrl: String,
            authInterceptor: Interceptor,
            appCache: AppCache? = null,
            logger: Interceptor? = null
        ): RestApiClient {
            return instance ?: synchronized(RestApiClient::class) {
                return instance ?: RestApiClient(
                    baseUrl,
                    authInterceptor,
                    appCache?.getCache(),
                    logger
                ).apply { instance = this }
            }
        }
    }

    private val okHttpClient: OkHttpClient by lazy {
        val okHttpBuilder = OkHttpClient.Builder()

        okHttpBuilder.connectTimeout(10, TimeUnit.SECONDS)
            .followRedirects(true)
            .followSslRedirects(true)
            .addInterceptor(authInterceptor)
            .retryOnConnectionFailure(true)

        cache?.let { okHttpBuilder.cache(it) }
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
