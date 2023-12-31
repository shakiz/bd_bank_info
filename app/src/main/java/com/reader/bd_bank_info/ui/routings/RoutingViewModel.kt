package com.reader.bd_bank_info.ui.routings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.reader.bd_bank_info.AppInjector
import com.reader.bd_bank_info.data.datasource.HomeApi
import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.data.model.Routings
import com.reader.bd_bank_info.data.repository.bank.BankRepositoryImpl
import com.reader.bd_bank_info.utils.ROUTINGS
import com.reader.bd_bank_info.utils.orFalse
import kotlinx.coroutines.launch

class RoutingViewModel: ViewModel(){
    private var bankRepository = BankRepositoryImpl(HomeApi(AppInjector.getRestApiClient("http://data.fixer.io/api/")))
    private var bankList = ArrayList<Bank>()
    private var filteredBankList = MutableLiveData<List<Bank>>()
    private var routingList = ArrayList<Routings>()
    private var filteredRoutingList = MutableLiveData<List<Routings>>()
    private var isDataLoading = MutableLiveData<Boolean>()

    companion object{
        const val TAG = "RoutingViewModel"
    }

    fun onDataLoadingStateChanged(): LiveData<Boolean>{
        return isDataLoading
    }

    fun onBankListFetched() : LiveData<List<Bank>?>{
        return filteredBankList
    }

    fun onRoutingListFetched() : LiveData<List<Routings>?>{
        return filteredRoutingList
    }

    fun fetchBankList(){
        viewModelScope.launch {
            val response = bankRepository.fetchBankList()
            bankList.addAll(response)
            filteredBankList.postValue(response)
        }
    }

    fun fetchRoutingByBankId(bankId: Int){
        isDataLoading.postValue(true)
        if(bankId <= 0){
            return
        }

        viewModelScope.launch {
            var tempData = mutableListOf<Routings?>()
            val routingDbReference = AppInjector.getRoutingTableData(ROUTINGS).child(bankId.toString())
            Log.e(TAG, "fetchRoutingByBankId: ${routingDbReference.parent}")

            routingDbReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dsRouting in snapshot.children) {
                        val routing: Routings? = dsRouting.getValue(Routings::class.java)
                        tempData.add(routing)
                        Log.i(TAG, "" + routing?.branchName)
                    }
                    tempData.let {
                        routingList.addAll(it as List<Routings>)
                        filteredRoutingList.postValue(it as List<Routings>)
                    }
                    isDataLoading.postValue(false)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.i(TAG, error.message)
                    isDataLoading.postValue(false)
                }
            })
        }
    }

    fun searchBankItem(bankName: String){
        if(bankName.isEmpty()){
            return
        }
        val newList = bankList.filter { bank: Bank -> bank.bankName?.lowercase()?.contains(bankName.lowercase()).orFalse() }
        filteredBankList.postValue(newList)
    }

    fun searchBankItemForRoutingByBranch(branchName: String){
        if (branchName.isEmpty()){
            return
        }
        val newList = routingList.filter { routing: Routings -> routing.branchName?.lowercase()?.contains(branchName.lowercase()).orFalse() }
        filteredRoutingList.postValue(newList)
    }
}