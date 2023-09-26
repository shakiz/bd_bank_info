package com.reader.bd_bank_info.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reader.bd_bank_info.AppInjector
import com.reader.bd_bank_info.data.datasource.HomeApi
import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.data.model.NavigationRail
import com.reader.bd_bank_info.data.repository.HomeRepositoryImpl
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel(){
    private var homeRepository = HomeRepositoryImpl(HomeApi(AppInjector.getRestApiClient("http://data.fixer.io/api/")))
    private var navigationRailList = MutableLiveData<List<NavigationRail>>()
    private var bankList = MutableLiveData<List<Bank>>()

    fun onNavigationRailFetched() : LiveData<List<NavigationRail>?>{
        return navigationRailList
    }

    fun onBankListFetched() : LiveData<List<Bank>?>{
        return bankList
    }

    fun fetchNavigationRailItems(){
        viewModelScope.launch {
            val response = homeRepository.fetchNavigationRailItems()
            navigationRailList.postValue(response)
        }
    }

    fun fetchBankList(){
        viewModelScope.launch {
            val response = homeRepository.fetchBankList()
            bankList.postValue(response)
        }
    }
}