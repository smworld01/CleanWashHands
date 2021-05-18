package com.hands.clean.function.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hands.clean.service.WashLocationServiceManager
import com.hands.clean.function.settings.WashSettingsManager

class ServiceStopReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val serviceManager = WashLocationServiceManager(context)
        val washSettings = WashSettingsManager(context)

        washSettings.gpsNotify = false
        washSettings.wifiNotify = false

        serviceManager.onStopService()
    }
}