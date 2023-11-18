package com.reader.bd_bank_info.data.repository.loan

import com.reader.bd_bank_info.data.datasource.HomeApi
import com.reader.bd_bank_info.data.model.LoanType

class BankLoanRepositoryImpl(private val homeApi: HomeApi) : BankLoanRepository {

    override suspend fun fetchBankLoanTypeList(): List<LoanType> {
        return fetchBankLoanTypeLocalList()
    }

    private fun fetchBankLoanTypeLocalList() : List<LoanType>{
        return listOf(
            LoanType(loanTypeIcon = 0, loanTypeName = "Car Loan", navigationUrl = "https://www.banksbd.org/loans/car-loan.html"),
            LoanType(loanTypeIcon = 0, loanTypeName = "Consumer Loan", navigationUrl = "https://www.banksbd.org/loans/consumer-loan.html"),
            LoanType(loanTypeIcon = 0, loanTypeName = "Education Loan", navigationUrl = "https://www.banksbd.org/loans/education-loan.html"),
            LoanType(loanTypeIcon = 0, loanTypeName = "Home Loan", navigationUrl = "https://www.banksbd.org/loans/home-loan.html"),
            LoanType(loanTypeIcon = 0, loanTypeName = "Personal Loan", navigationUrl = "https://www.banksbd.org/loans/personal-loan.html"),
            LoanType(loanTypeIcon = 0, loanTypeName = "SME Loan", navigationUrl = "https://www.banksbd.org/loans/sme-loan.html"),
            LoanType(loanTypeIcon = 0, loanTypeName = "Business Loan", navigationUrl = "https://www.banksbd.org/loans/business-loan.html"),
            LoanType(loanTypeIcon = 0, loanTypeName = "Overseas Job Loan", navigationUrl = "https://www.banksbd.org/loans/overseas-job-loan.html")
        )
    }
}