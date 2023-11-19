package com.reader.bd_bank_info.ui.loan

import com.reader.bd_bank_info.data.model.loan.LoanType
import com.reader.bd_bank_info.data.model.loan.PopularLoan

interface LoanCallBack {
    fun onLoanTypeClick(loanType: LoanType)
    fun onPopularLoanClick(popularLoan: PopularLoan)
}