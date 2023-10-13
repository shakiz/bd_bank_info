package com.reader.bd_bank_info.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StockMarket(
    val bankId: Int? = null,
    val bankName: String? = null,
    val bankIconRes: Int? = null,
    val stockCode: String? = null
) : Parcelable