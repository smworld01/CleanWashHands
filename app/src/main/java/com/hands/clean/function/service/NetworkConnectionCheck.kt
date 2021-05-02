package com.hands.clean.function.service

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.hands.clean.function.notification.NewDeviceNotify
import com.hands.clean.function.notification.WashNotify
import com.hands.clean.function.notification.type.NotifyType
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entrys.WifiEntry
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
        if(!settings.wifiNotify) return

        thread {
            val wifiManager: WifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiList = wifiManager.scanResults
            val info: WifiInfo = wifiManager.connectionInfo


            if(wifiList.isEmpty()) {
                // Todo GPS 권한이 없거나 GPS가 꺼짐
                return@thread
            }

            val wifiInfo = Info(info.ssid.replace("\"", ""), info.bssid)

            val currentWifi = wifiList.filter {
                it.BSSID == wifiInfo.bssid
            }

            notifyEncryption(currentWifi)

            val queryWifi = DB.getInstance(context).wifiDao().findByAddress(wifiInfo.bssid)
            if(queryWifi == null) {
                notifyNewWifiDevice(wifiInfo)
            } else {
                if (queryWifi.isNotification) {
                    WashNotify(context, NotifyType.Wifi).send("와이파이 기기 ${wifiInfo.ssid}에 연결되었습니다.")
                }
            }
        }
    }
    private fun notifyEncryption(currentWifi: List<ScanResult>) {
        if (currentWifi.isNotEmpty())
            if (settings.wifiEncryptionNotify) {
                val capabilities = currentWifi[0].capabilities
                if(capabilities.contains("WPA") || capabilities.contains("WEP")) {
                    // Todo 암호가 있는 와이파이 접속. 손 씻으라고 알림
                    WashNotify(context, NotifyType.Wifi).send("암호가 있는 와이파이에 접속되었습니다.")
                }
            }
    }
    private fun notifyNewWifiDevice(wifiInfo: Info) {
        if (settings.wifiNewDeviceNotify) {
            val currentWifiEntry = WifiEntry(0, wifiInfo.ssid, wifiInfo.bssid, false)
            DB.getInstance().wifiDao().insertAll(currentWifiEntry)

            NewDeviceNotify(context, NotifyType.Wifi).send(wifiInfo.bssid)
        }
    }

    class Info (val ssid: String, val bssid: String)
}