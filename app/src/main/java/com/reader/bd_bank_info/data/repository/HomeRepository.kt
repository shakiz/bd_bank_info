package com.reader.bd_bank_info.data.repository

import com.reader.bd_bank_info.data.model.NavigationRail


interface HomeRepository {
    suspend fun fetchNavigationRailItems() : List<NavigationRail>
}