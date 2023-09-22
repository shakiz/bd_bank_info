package com.reader.bd_bank_info.common.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class ConnectionStateMonitor(private val listener: ConnectionChangeListener) : ConnectivityManager.NetworkCallback() {

    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .build()

    fun enable(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(networkRequest, this)

        val netInfo = connectivityManager.activeNetworkInfo
        if (netInfo == null || !netInfo.isConnected) {
            listener.onConnectionLost()
        }
    }

    fun disable(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.unregisterNetworkCallback(this)
    }

    override fun onAvailable(network: Network) {
        listener.onReconnected()
    }

    override fun onLost(network: Network) {
        listener.onConnectionLost()
    }

    interface ConnectionChangeListener {

        fun onConnectionLost()

        fun onReconnected()
    }
}