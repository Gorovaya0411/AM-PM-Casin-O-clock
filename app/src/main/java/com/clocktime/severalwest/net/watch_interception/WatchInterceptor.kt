package com.clocktime.severalwest.net.watch_interception

import com.clocktime.severalwest.net.exclusion.NoInternetException
import com.clocktime.severalwest.net.service.InternetConnectionService
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class WatchInterceptor @Inject constructor(
    private val connectionService: InternetConnectionService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            if (!connectionService.isConnected()) {
                throw NoInternetException(RuntimeException())
            }
            return chain.proceed(chain.request())
        } catch (e: IOException) {
            if (!connectionService.isConnected()) {
                throw NoInternetException(e)
            }
            throw e
        }
    }

}