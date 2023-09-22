package com.reader.bd_bank_info.data.repository

import com.reader.bd_bank_info.data.datasource.HomeApi
import com.reader.bd_bank_info.data.model.NavigationRail

class HomeRepositoryImpl(private val homeApi: HomeApi) : HomeRepository {
    override suspend fun fetchMovieList(): List<NavigationRail> {
        return homeApi.fetchMovieList()
    }
}