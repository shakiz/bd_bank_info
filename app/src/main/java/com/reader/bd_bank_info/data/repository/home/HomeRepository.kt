package com.reader.bd_bank_info.data.repository.home

import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.data.model.NavigationRail


interface HomeRepository {
    suspend fun fetchNavigationRailItems() : List<NavigationRail>
    suspend fun fetchBankList() : List<Bank>
}