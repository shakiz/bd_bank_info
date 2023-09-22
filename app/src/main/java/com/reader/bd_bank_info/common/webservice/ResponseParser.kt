package com.reader.bd_bank_info.common.webservice

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response

object ResponseParser {

    fun <T> parse(response: Response<T>): T {
        return response.body()
            ?: throw ApiException(
                response.code(),
                parseApiError(response.errorBody(), ApiError::class.java),
            )
    }

    private fun <T> parseApiError(errorBody: ResponseBody?, errorModel: Class<T>): T? {
        errorBody ?: return null

        return try {
            Gson().fromJson(errorBody.string(), errorModel)
        } catch (ex: Exception) {
            null
        }
    }

    private fun getErrorMessage(errorBody: ResponseBody?): String? {
        return parseApiError(errorBody, ApiError::class.java)?.message
    }
}
