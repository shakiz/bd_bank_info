package com.reader.bd_bank_info.common.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reader.bd_bank_info.common.LoaderState
import com.reader.bd_bank_info.common.common.EventLiveData
import com.reader.bd_bank_info.common.webservice.ApiException
import com.reader.bd_bank_info.common.webservice.UnauthorizedException
import okio.IOException

typealias RetryAction = () -> Unit

abstract class BaseViewModel : ViewModel() {

    protected val blockingLoaderState: MutableLiveData<LoaderState> by lazy { MutableLiveData() }
    protected val remoteMessage = EventLiveData<String?>()

    private val unauthorizedError = EventLiveData<Unit>()
    private val connectionError = EventLiveData<RetryAction?>()
    private val unknownError = EventLiveData<Unit>()

    fun onBlockingLoaderStateChanged(): LiveData<LoaderState> {
        return blockingLoaderState
    }

    fun onRemoteMessageReceived(): LiveData<String?> {
        return remoteMessage
    }

    fun onUnauthorizedError(): LiveData<Unit> {
        return unauthorizedError
    }

    fun onConnectionError(): LiveData<RetryAction?> {
        return connectionError
    }

    fun onUnknownError(): LiveData<Unit> {
        return unknownError
    }

    protected fun handleCommonError(error: Throwable, retryAction: RetryAction? = null): Boolean {
        val apiException = error as? ApiException

        if (error is UnauthorizedException || apiException?.code == 401) {
            unauthorizedError.postValue(Unit)
            return true
        }

        if (error is IOException) {
            connectionError.postValue(retryAction)
            return true
        }

        return false
    }
}
