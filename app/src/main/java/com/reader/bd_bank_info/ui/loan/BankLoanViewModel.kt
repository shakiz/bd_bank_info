package com.reader.bd_bank_info.ui.loan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reader.bd_bank_info.AppInjector
import com.reader.bd_bank_info.data.datasource.HomeApi
import com.reader.bd_bank_info.data.model.loan.LoanType
import com.reader.bd_bank_info.data.repository.loan.BankLoanRepositoryImpl
import kotlinx.coroutines.launch

class BankLoanViewModel : ViewModel() {
    private var bankLoanRepository =
        BankLoanRepositoryImpl(HomeApi(AppInjector.getRestApiClient("http://data.fixer.io/api/")))
    private var bankLoanTypeList = MutableLiveData<List<LoanType>>()

    fun onBankTypeListFetched(): LiveData<List<LoanType>> {
        return bankLoanTypeList
    }

    fun fetchBankList() {
        viewModelScope.launch {
            val response = bankLoanRepository.fetchBankLoanTypeList()
            bankLoanTypeList.postValue(response)
        }
    }
}