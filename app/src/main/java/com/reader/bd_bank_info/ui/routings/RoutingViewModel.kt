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
    private var routingList = MutableLiveData<List<Routings>>()

    companion object{
        const val TAG = "RoutingViewModel"
    }

    fun onBankListFetched() : LiveData<List<Bank>?>{
        return filteredBankList
    }

    fun onRoutingListFetched() : LiveData<List<Routings>?>{
        return routingList
    }

    fun fetchBankList(){
        viewModelScope.launch {
            val response = bankRepository.fetchBankList()
            bankList.addAll(response)
            filteredBankList.postValue(response)
        }
    }

    fun fetchRoutingByBankId(bankId: Int){
        if(bankId <= 0){
            return
        }

        viewModelScope.launch {
            var tempData = mutableListOf<Routings?>()
            val routingDbReference = AppInjector.getRoutingTableData(ROUTINGS).child(bankId.toString())
            Log.e(TAG, "fetchRoutingByBankId: ${routingDbReference.parent}")

            routingDbReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.children != null) {
                        for (dataSnapshot in snapshot.children) {
                            val routing: Routings? = dataSnapshot.getValue(Routings::class.java)
                            tempData.add(routing)
                            Log.i(TAG, "" + routing?.branchName)
                        }
                        tempData.let {
                            routingList.postValue(it as List<Routings>)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.i(TAG, "" + error.message)
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
}