package com.github.demo.utils.network

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Singleton


@Singleton
class NetworkHelper constructor(val context: Context) {

    fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork?.isConnected ?: false
    }

}