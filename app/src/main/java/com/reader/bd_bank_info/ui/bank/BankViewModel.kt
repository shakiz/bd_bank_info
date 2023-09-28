package com.reader.bd_bank_info.ui.bank

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reader.bd_bank_info.AppInjector
import com.reader.bd_bank_info.data.datasource.HomeApi
import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.data.repository.bank.BankRepositoryImpl
import kotlinx.coroutines.launch

class BankViewModel: ViewModel(){
    private var bankRepository = BankRepositoryImpl(HomeApi(AppInjector.getRestApiClient("http://data.fixer.io/api/")))
    private var bankList = MutableLiveData<List<Bank>>()

    fun onBankListFetched() : LiveData<List<Bank>?>{
        return bankList
    }

    fun fetchBankList(){
        viewModelScope.launch {
            val response = bankRepository.fetchBankList()
            bankList.postValue(response)
        }
    }
}