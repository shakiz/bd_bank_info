package com.reader.bd_bank_info.common.webservice

class ApiException(val code: Int, val error: ApiError?) : Exception(error?.message)

class UnauthorizedException(message: String? = null) : Exception(message)
