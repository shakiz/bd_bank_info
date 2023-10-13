package com.reader.bd_bank_info.data.repository.home

import com.reader.bd_bank_info.R
import com.reader.bd_bank_info.data.datasource.HomeApi
import com.reader.bd_bank_info.data.model.Bank
import com.reader.bd_bank_info.data.model.ExtraContent
import com.reader.bd_bank_info.data.model.NavigationRail
import com.reader.bd_bank_info.utils.*

class HomeRepositoryImpl(private val homeApi: HomeApi) : HomeRepository {
    override suspend fun fetchNavigationRailItems(): List<NavigationRail> {
        return fetchLocalNavigationRailData()
    }

    private fun fetchLocalNavigationRailData(): List<NavigationRail> {
        return listOf(
            NavigationRail(titleEn = "Banks", titleBn = "Banks", identifier = IDENTIFIER_BANK),
            NavigationRail(
                titleEn = "Swift Codes",
                titleBn = "Swift Codes",
                identifier = IDENTIFIER_SWIFT_CODE
            ),
            NavigationRail(
                titleEn = "Currency Rates",
                titleBn = "Currency Rates",
                identifier = IDENTIFIER_CURRENCY_RATES,
                content = ExtraContent(url = "https://www.banksbd.org/exchange.html")
            ),
            NavigationRail(
                titleEn = "Routings",
                titleBn = "Routings",
                identifier = IDENTIFIER_ROUTING_NO
            ),
            NavigationRail(titleEn = "Loan", titleBn = "Loan", identifier = IDENTIFIER_LOAN),
            NavigationRail(
                titleEn = "Support",
                titleBn = "Support",
                identifier = IDENTIFIER_SUPPORT
            )
        )
    }
}