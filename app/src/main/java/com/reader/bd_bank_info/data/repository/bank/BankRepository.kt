package com.reader.bd_bank_info.data.repository.bank

import com.reader.bd_bank_info.data.model.Bank


interface BankRepository {
    suspend fun fetchBankList() : List<Bank>
}