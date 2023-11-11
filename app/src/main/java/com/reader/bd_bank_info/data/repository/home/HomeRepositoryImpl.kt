package com.reader.bd_bank_info.data.repository.home

import com.reader.bd_bank_info.R
import com.reader.bd_bank_info.data.datasource.HomeApi
import com.reader.bd_bank_info.data.model.ExtraContent
import com.reader.bd_bank_info.data.model.MainMenuItem
import com.reader.bd_bank_info.utils.*

class HomeRepositoryImpl(private val homeApi: HomeApi) : HomeRepository {
    override suspend fun fetchNavigationRailItems(): List<MainMenuItem> {
        return fetchLocalNavigationRailData()
    }

    private fun fetchLocalNavigationRailData(): List<MainMenuItem> {
        return listOf(
            MainMenuItem(
                titleEn = "Banks",
                titleBn = "Banks",
                identifier = IDENTIFIER_BANK,
                icon = R.drawable.ic_bank_icon
            ),
            MainMenuItem(
                titleEn = "Swift Codes",
                titleBn = "Swift Codes",
                identifier = IDENTIFIER_SWIFT_CODE,
                icon = R.drawable.ic_swift_key
            ),
            MainMenuItem(
                titleEn = "Routings",
                titleBn = "Routings",
                identifier = IDENTIFIER_ROUTING_NO,
                icon = R.drawable.ic_routing_no
            ),
            MainMenuItem(
                titleEn = "Stock Market",
                titleBn = "Stock Market",
                identifier = IDENTIFIER_STOCK_MARKET,
                icon = R.drawable.ic_stock_market
            ),
            MainMenuItem(
                titleEn = "Currency Rates",
                titleBn = "Currency Rates",
                identifier = IDENTIFIER_CURRENCY_RATES,
                icon = R.drawable.ic_exchange_rate,
                content = ExtraContent(url = "https://www.banksbd.org/exchange.html")
            ),
//            MainMenuItem(
//                titleEn = "Loan",
//                titleBn = "Loan",
//                identifier = IDENTIFIER_LOAN,
//                icon = R.drawable.ic_bank_loan
//            ),
        )
    }
}