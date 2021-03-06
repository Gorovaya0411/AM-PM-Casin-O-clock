package com.clocktime.severalwest.net.service

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import javax.inject.Inject

interface InternetConnectionService {
    fun isConnected(): Boolean
}

class InternetConnectionServiceImpl @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : InternetConnectionService {

    @SuppressLint("MissingPermission")
    override fun isConnected(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}