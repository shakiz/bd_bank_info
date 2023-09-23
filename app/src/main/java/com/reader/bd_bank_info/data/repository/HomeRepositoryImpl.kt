package com.reader.bd_bank_info.data.repository

import com.reader.bd_bank_info.data.datasource.HomeApi
import com.reader.bd_bank_info.data.model.NavigationRail

class HomeRepositoryImpl(private val homeApi: HomeApi) : HomeRepository {
    override suspend fun fetchNavigationRailItems(): List<NavigationRail> {
        return fetchLocalNavigationRailData()
    }

    private fun fetchLocalNavigationRailData() : List<NavigationRail>{
        return listOf(
            NavigationRail(titleEn = "Home", titleBn = "Home", identifier = "home"),
            NavigationRail(titleEn = "Currency Rates", titleBn = "Currency Rates", identifier = "currency-rates"),
            NavigationRail(titleEn = "Loan", titleBn = "Loan", identifier = "loan"),
            NavigationRail(titleEn = "Swift Codes", titleBn = "Swift Codes", identifier = "swift-codes"),
            NavigationRail(titleEn = "Support", titleBn = "Support", identifier = "Support")
        )
    }
}