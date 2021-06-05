package com.hands.clean.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import com.hands.clean.function.settings.WashSettingsManager
import com.hands.clean.service.TrackerServiceManager

class ServiceStopReceiver : BroadcastReceiver() {
    private lateinit var mContext: Context

    override fun onReceive(context: Context, intent: Intent) {
        mContext = context

        when (intent.action) {
            LocationManager.PROVIDERS_CHANGED_ACTION -> {
                changeService()
            }
            else -> {
                stopService(context)
            }
        }
    }
    private fun changeService() {
        val serviceManager = TrackerServiceManager(mContext)
        serviceManager.changeService()
    }

    private fun stopService(context: Context) {
        val serviceManager = TrackerServiceManager(context)
        val washSettings = WashSettingsManager(context)

        washSettings.gpsNotify = false
        washSettings.wifiNotify = false

        serviceManager.onStopService()
    }
}