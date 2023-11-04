package com.reader.bd_bank_info

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.reader.bd_bank_info.service.RetrofitService

object AppInjector {
    fun getRestApiClient(baseUrl: String): RetrofitService {
        return RetrofitService.getInstance(baseUrl)
    }

    fun initFireBaseDb(context: Context){
        FirebaseApp.initializeApp(context)
    }

    private fun getFirebaseDatabase() : FirebaseDatabase{
        return FirebaseDatabase.getInstance()
    }

    fun getRoutingTableData(pathName: String) : DatabaseReference {
        return getFirebaseDatabase().getReference(pathName)
    }
}
