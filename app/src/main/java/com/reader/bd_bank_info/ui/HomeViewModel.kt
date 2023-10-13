package com.reader.bd_bank_info.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reader.bd_bank_info.AppInjector
import com.reader.bd_bank_info.data.datasource.HomeApi
import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.data.model.MainMenuItem
import com.reader.bd_bank_info.data.repository.bank.BankRepositoryImpl
import com.reader.bd_bank_info.data.repository.home.HomeRepositoryImpl
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel(){
    private var homeRepository = HomeRepositoryImpl(HomeApi(AppInjector.getRestApiClient("http://data.fixer.io/api/")))
    private var bankRepository = BankRepositoryImpl(HomeApi(AppInjector.getRestApiClient("http://data.fixer.io/api/")))
    private var mainMenuItemList = MutableLiveData<List<MainMenuItem>>()
    private var bankList = MutableLiveData<List<Bank>>()

    fun onNavigationRailFetched() : LiveData<List<MainMenuItem>?>{
        return mainMenuItemList
    }

    fun onBankListFetched() : LiveData<List<Bank>?>{
        return bankList
    }

    fun fetchNavigationRailItems(){
        viewModelScope.launch {
            val response = homeRepository.fetchNavigationRailItems()
            mainMenuItemList.postValue(response)
        }
    }

    fun fetchBankList(){
        viewModelScope.launch {
            val response = bankRepository.fetchBankList().take(5)
            bankList.postValue(response)
        }
    }
}