package com.reader.bd_bank_info.ui.bank

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reader.bd_bank_info.AppInjector
import com.reader.bd_bank_info.data.datasource.HomeApi
import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.data.repository.bank.BankRepositoryImpl
import com.reader.bd_bank_info.utils.orFalse
import kotlinx.coroutines.launch

class BankViewModel: ViewModel(){
    private var bankRepository = BankRepositoryImpl(HomeApi(AppInjector.getRestApiClient("http://data.fixer.io/api/")))
    private var bankList = ArrayList<Bank>()
    private var filteredBankList = MutableLiveData<List<Bank>>()

    fun onBankListFetched() : LiveData<List<Bank>?>{
        return filteredBankList
    }

    fun fetchBankList(){
        viewModelScope.launch {
            val response = bankRepository.fetchBankList()
            bankList.addAll(response)
            filteredBankList.postValue(response)
        }
    }

    fun searchBankItem(bankName: String){
        if(bankName.isEmpty()){
            return
        }
        val newList = bankList.filter { bank: Bank -> bank.bankName?.lowercase()?.contains(bankName.lowercase()).orFalse() }
        filteredBankList.postValue(newList)
    }
}