package com.reader.bd_bank_info.data.repository.loan

import com.reader.bd_bank_info.data.model.LoanType


interface BankLoanRepository {
    suspend fun fetchBankLoanTypeList() : List<LoanType>
}