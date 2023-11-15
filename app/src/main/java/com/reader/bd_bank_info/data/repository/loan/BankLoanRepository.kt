package com.reader.bd_bank_info.data.repository.loan

import com.reader.bd_bank_info.data.model.Bank


interface BankLoanRepository {
    suspend fun fetchBankList() : List<Bank>
}