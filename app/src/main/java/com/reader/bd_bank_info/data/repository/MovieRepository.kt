package com.reader.bd_bank_info.data.repository

import com.reader.bd_bank_info.data.model.Movie


interface MovieRepository {
    suspend fun fetchMovieList() : List<Movie>
}