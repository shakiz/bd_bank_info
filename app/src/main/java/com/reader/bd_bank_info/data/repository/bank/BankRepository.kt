package com.reader.bd_bank_info.data.repository.bank

import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.data.model.NavigationRail


interface BankRepository {
    suspend fun fetchNavigationRailItems() : List<NavigationRail>
    suspend fun fetchBankList() : List<Bank>
}