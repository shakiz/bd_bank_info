package com.reader.bd_bank_info.data.model

data class Bank(
    val bankId: Int? = null,
    val bankName: String? = null,
    val bankIconRes: Int? = null,
    val legalStatus: String? = null,
    val establishedDate: Int? = null,
    val bankType: String? = null,
    val bankCategory: String? = null,
    val origin: String? = null,
    val corporateAddress: String? = null,
    val hotlineNo: Int? = null,
    val hotlinePhoneNo: String? = null,
    val email: String? = null,
    val address: String? = null,
    val swiftCode: String? = null,
    val stockCode: String? = null
)