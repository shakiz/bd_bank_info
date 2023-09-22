package com.reader.bd_bank_info.data.datasource

import android.util.Log
import com.example.kotlin_coroutine_mvvm.service.RetrofitService
import com.reader.bd_bank_info.data.model.Movie

class MovieApi(private val retrofitService: RetrofitService) {
    suspend fun fetchMovieList(): List<Movie> {
        val response = retrofitService.fetchAllMovie()
        Log.e("shakil","${response.body()}")
        return response.body()!!
    }
}