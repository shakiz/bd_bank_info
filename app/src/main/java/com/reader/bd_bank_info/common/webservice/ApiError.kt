package com.reader.bd_bank_info.common.webservice

import com.google.gson.annotations.SerializedName

data class ApiError(

    @SerializedName("status_code")
    var statusCode: Int = 0,

    @SerializedName("status")
    var status: String = "",

    @SerializedName("message")
    var message: String? = null,

    @SerializedName("error")
    var error: Error? = null
)
