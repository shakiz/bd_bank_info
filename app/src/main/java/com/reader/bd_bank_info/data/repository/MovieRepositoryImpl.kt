package com.reader.bd_bank_info.data.repository

import com.reader.bd_bank_info.data.datasource.MovieApi
import com.reader.bd_bank_info.data.model.Movie

class MovieRepositoryImpl(private val movieApi: MovieApi) : MovieRepository {
    override suspend fun fetchMovieList(): List<Movie> {
        return movieApi.fetchMovieList()
    }
}