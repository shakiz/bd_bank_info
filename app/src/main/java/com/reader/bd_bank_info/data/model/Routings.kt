package com.reader.bd_bank_info.data.model

import com.google.firebase.database.PropertyName

data class Routings(
    @PropertyName("districts")
    val districts: String? = "",
    @PropertyName("branchName")
    val branchName: String? = "",
    @PropertyName("routingNo")
    val routingNo: Int? = 0
)
