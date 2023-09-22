package com.reader.bd_bank_info.data.datasource

import android.util.Log
import com.reader.bd_bank_info.data.model.NavigationRail
import com.reader.bd_bank_info.service.RetrofitService

class HomeApi(private val retrofitService: RetrofitService) {
    suspend fun fetchMovieList(): List<NavigationRail> {
        val response = retrofitService.fetchAllMovie()
        Log.e("shakil","${response.body()}")
        return response.body()!!
    }
}