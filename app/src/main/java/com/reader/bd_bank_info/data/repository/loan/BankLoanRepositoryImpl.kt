package com.reader.bd_bank_info.data.repository.loan

import com.reader.bd_bank_info.R
import com.reader.bd_bank_info.data.datasource.HomeApi
import com.reader.bd_bank_info.data.model.loan.LoanType
import com.reader.bd_bank_info.data.model.loan.PopularLoan

class BankLoanRepositoryImpl(private val homeApi: HomeApi) : BankLoanRepository {

    override suspend fun fetchBankLoanTypeList(): List<LoanType> {
        return fetchBankLoanTypeLocalList()
    }

    override suspend fun fetchPopularBankLoanList(): List<PopularLoan> {
        return fetchPopularBankLoanLocalList()
    }

    private fun fetchBankLoanTypeLocalList() : List<LoanType>{
        return listOf(
            LoanType(loanTypeIcon = R.drawable.ic_car_loan, loanTypeName = "Car Loan", navigationUrl = "https://www.banksbd.org/loans/car-loan.html"),
            LoanType(loanTypeIcon = R.drawable.ic_consumer_loan, loanTypeName = "Consumer Loan", navigationUrl = "https://www.banksbd.org/loans/consumer-loan.html"),
            LoanType(loanTypeIcon = R.drawable.ic_education_loan, loanTypeName = "Education Loan", navigationUrl = "https://www.banksbd.org/loans/education-loan.html"),
            LoanType(loanTypeIcon = R.drawable.ic_home_loan, loanTypeName = "Home Loan", navigationUrl = "https://www.banksbd.org/loans/home-loan.html"),
            LoanType(loanTypeIcon = R.drawable.ic_personal_loan, loanTypeName = "Personal Loan", navigationUrl = "https://www.banksbd.org/loans/personal-loan.html"),
            LoanType(loanTypeIcon = R.drawable.ic_sme_loan, loanTypeName = "SME Loan", navigationUrl = "https://www.banksbd.org/loans/sme-loan.html"),
            LoanType(loanTypeIcon = R.drawable.ic_business_loan, loanTypeName = "Business Loan", navigationUrl = "https://www.banksbd.org/loans/business-loan.html"),
            LoanType(loanTypeIcon = R.drawable.ic_job_loan, loanTypeName = "Overseas Job Loan", navigationUrl = "https://www.banksbd.org/loans/overseas-job-loan.html")
        )
    }

    private fun fetchPopularBankLoanLocalList(): List<PopularLoan>{
        return listOf(
            PopularLoan(bankId = 15, bankName = "DBBL", loanName = "DBBL Jokhon Tokhon Personal Loan", navigationUrl = "https://www.banksbd.org/dbbl/dbbl-jokhon-tokhon-personal-loan.html"),
            PopularLoan(bankId = 31, bankName = "NCC Bank", loanName = "Education Loan by NCC Bank", navigationUrl = "https://www.banksbd.org/nccl/education-loan.html"),
            PopularLoan(bankId = 31, bankName = "NCC Bank", loanName = "Personal Loan by NCC Bank", navigationUrl = "https://www.banksbd.org/nccl/personal-loan.html"),
            PopularLoan(bankId = 1, bankName = "AB Bank", loanName = "ABBL Sathi Loan", navigationUrl = "https://www.banksbd.org/abbl/abbl-sathi-loan.html"),
            PopularLoan(bankId = 26, bankName = "Mercantile Bank Limited", loanName = "MBL Education Loan", navigationUrl = "https://www.banksbd.org/mblb/education-loan.html"),
            PopularLoan(bankId = 33, bankName = "One Bank", loanName = "One Bank Student Loan", navigationUrl = "https://www.banksbd.org/obl/student-loan.html"),
            PopularLoan(bankId = 1, bankName = "AB Bank", loanName = "ABBL Digun Loan", navigationUrl = "https://www.banksbd.org/abbl/abbl-digun-loan.html"),
            PopularLoan(bankId = 1, bankName = "AB Bank", loanName = "ABBL Proshar Loan", navigationUrl = "https://www.banksbd.org/abbl/abbl-proshar-loan.html"),
            PopularLoan(bankId = 15, bankName = "DBBL", loanName = "DBBL Thikana Home Loan", navigationUrl = "https://www.banksbd.org/dbbl/dbbl-thikana-home-loan.html"),
            PopularLoan(bankId = 15, bankName = "DBBL", loanName = "DBBL Prottasha Car Loan", navigationUrl = "https://www.banksbd.org/dbbl/dbbl-prottasha-car-loan.html"),
        )
    }
}