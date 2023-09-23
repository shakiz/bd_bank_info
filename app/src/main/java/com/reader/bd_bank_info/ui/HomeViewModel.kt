package com.reader.bd_bank_info.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reader.bd_bank_info.data.model.NavigationRail
import com.reader.bd_bank_info.data.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel(){
    private var navigationRailList = MutableLiveData<List<NavigationRail>>()

    fun fetchNavigationRailItems(){
        viewModelScope.launch {
            val response = homeRepository.fetchNavigationRailItems()
            navigationRailList.postValue(response)
        }
    }
}