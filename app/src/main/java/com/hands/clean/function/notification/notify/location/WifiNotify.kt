package com.hands.clean.function.notification.notify.location

import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.util.Log
import com.hands.clean.function.notification.factory.notify.EncryptionWifiNotifyFactory
import com.hands.clean.function.notification.factory.notify.WashNotifyFactory
import com.hands.clean.function.notification.notify.Notify
import com.hands.clean.function.room.entry.DeviceEntry
import com.hands.clean.function.room.entry.WifiEntry
import com.hands.clean.function.settings.WashSettingsManager

class WifiNotify(context: Context) : Notify {
    private val notify = NotifyProcess(context)

    override fun onNotify() {
        notify.onNotify()
    }

    class NotifyProcess(override val context: Context) : DeviceNotify() {
        private val settings = WashSettingsManager(context)
        private val deviceInfo = getDeviceInContent()

        override var deviceEntry: DeviceEntry = WifiEntry(
            0,
            "",
            deviceInfo.SSID,
            deviceInfo.BSSID,
            false
        )

        override fun doNotify() {
            super.doNotify()

            if (isEncryptionNotify()) {
                EncryptionWifiNotifyFactory(context, deviceEntry as WifiEntry).onBuild()
                    .onNotify()
            }
        }

        private fun isEncryptionNotify(): Boolean {
            val capabilities = deviceInfo.capabilities

            return settings.wifiEncryptionNotify &&
                    (capabilities.contains("WPA") || capabilities.contains("WEP"))
        }

        private fun getDeviceInContent(): ScanResult {
            val wifiManager: WifiManager =
                context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiList = wifiManager.scanResults
            val wifiInfo: WifiInfo = wifiManager.connectionInfo

            val currentWifi = wifiList.filter {
                it.BSSID == wifiInfo.bssid
            }
            Log.e("test", wifiList.toString())
            Log.e("test", currentWifi.toString())

            if (currentWifi.isNullOrEmpty()) throw Exception()

            return currentWifi[0]
        }

        override fun notifySettingsIsEnable(): Boolean {
            return settings.wifiNotify
        }

        override fun newDeviceNotifySettingsIsEnable(): Boolean {
            return settings.wifiNewDeviceNotify
        }

        override fun sendNotify(foundDevice: DeviceEntry) {
            if (foundDevice.isNotification) {
                WashNotifyFactory(context, foundDevice).onBuild()
                    .onNotify()
            }
        }
    }
}