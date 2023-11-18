package com.reader.bd_bank_info.data.repository.loan

import com.reader.bd_bank_info.data.model.loan.LoanType


interface BankLoanRepository {
    suspend fun fetchBankLoanTypeList() : List<LoanType>
}