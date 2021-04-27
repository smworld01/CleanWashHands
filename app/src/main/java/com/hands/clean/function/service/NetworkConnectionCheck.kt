package com.hands.clean.function.service

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class NetworkConnectionCheck(val context: Context, val intent: Intent): ConnectivityManager.NetworkCallback() {
    private var networkRequest: NetworkRequest = NetworkRequest.Builder()
//            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
    private var connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun register() = this.connectivityManager.registerNetworkCallback(networkRequest, this)
    fun unregister() = this.connectivityManager.unregisterNetworkCallback(this);

    override fun onAvailable(network: Network) {
        super.onAvailable(network)

        val wifiManager: WifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info: WifiInfo = wifiManager.connectionInfo
        val state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)
        Log.e("test", "연결됨")


        Log.e("test", state.toString())
        if(info.bssid != null) {
            Log.e("test", info.bssid)
        }
        Log.e("test", info.toString())
    }

    override fun onLost(network: Network) {
        super.onLost(network)

        Log.e("test", "해제됨")
    }
}