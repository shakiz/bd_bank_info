package com.reader.bd_bank_info.data.repository.home

import com.reader.bd_bank_info.data.model.MainMenuItem


interface HomeRepository {
    suspend fun fetchNavigationRailItems() : List<MainMenuItem>
}