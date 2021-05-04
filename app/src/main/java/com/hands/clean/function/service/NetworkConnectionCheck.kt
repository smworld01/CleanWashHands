package com.hands.clean.function.service

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.hands.clean.function.notification.notify.WifiNotify
import com.hands.clean.function.settings.WashSettingsManager
import kotlin.concurrent.thread

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class NetworkConnectionCheck(val context: Context, val intent: Intent): ConnectivityManager.NetworkCallback() {
    private val settings = WashSettingsManager(context)

    private var networkRequest: NetworkRequest = NetworkRequest.Builder()
//            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
    private var connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun register() = this.connectivityManager.registerNetworkCallback(networkRequest, this)
    fun unregister() = this.connectivityManager.unregisterNetworkCallback(this);

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        thread {
            try {
                WifiNotify(context).onNotify()
            } catch(e: Exception) {
                Log.e("test", "가져오기 실패")
            }
        }
    }
}