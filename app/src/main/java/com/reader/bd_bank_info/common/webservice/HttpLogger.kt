package com.reader.bd_bank_info.common.webservice

import okhttp3.Interceptor

interface HttpLogger {
    fun getLoggingInterceptor(): Interceptor
}

