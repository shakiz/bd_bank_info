package com.reader.bd_bank_info.common.webservice

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

class HttpLoggerImpl private constructor(): HttpLogger {

    companion object {

        @Volatile
        private var instance: HttpLoggerImpl? = null

        fun getInstance() : HttpLoggerImpl {
            return instance ?: synchronized(HttpLoggerImpl::class) {
                return@synchronized instance ?: HttpLoggerImpl().apply { instance = this }
            }
        }
    }

    private val loggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    override fun getLoggingInterceptor(): Interceptor {
        return loggingInterceptor
    }
}
