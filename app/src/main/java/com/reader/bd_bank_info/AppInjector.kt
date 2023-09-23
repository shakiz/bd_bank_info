package com.reader.bd_bank_info

import com.reader.bd_bank_info.service.RetrofitService

object AppInjector {
    fun getRestApiClient(BASE_URL: String): RetrofitService {
        return RetrofitService.getInstance(BASE_URL)
    }

}
