package com.codefrnd.mvvmapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.codefrnd.mvvmapp.uitl.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(
    context: Context
) : Interceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable())
            throw NoInternetException("Make sure you have active internet connection")

        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // FIND NON DEPRECATED VERSION
        connectivityManager.activeNetworkInfo.also {
            return it != null
        }
    }
}