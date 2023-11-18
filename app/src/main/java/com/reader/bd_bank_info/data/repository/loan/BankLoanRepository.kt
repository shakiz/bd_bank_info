package com.reader.bd_bank_info.data.repository.loan

import com.reader.bd_bank_info.data.model.loan.LoanType
import com.reader.bd_bank_info.data.model.loan.PopularLoan


interface BankLoanRepository {
    suspend fun fetchBankLoanTypeList() : List<LoanType>
    suspend fun fetchPopularBankLoanList() : List<PopularLoan>
}