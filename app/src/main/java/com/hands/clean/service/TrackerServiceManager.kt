package com.hands.clean.service

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat.startForegroundService
import com.hands.clean.function.gps.SystemSettingsGpsChecker
import com.hands.clean.function.settings.WashSettingsManager

class TrackerServiceManager(
    private val context: Context
) {
    private val washSettingsManager = WashSettingsManager(context)
    private val emptyServiceManager = EmptyServiceManager(context)
    private val washLocationServiceManager = WashLocationServiceManager(context)

    fun onStartService() {
        if (SystemSettingsGpsChecker(context).isEnabled()) {
            washLocationServiceManager.onStartService()
        } else {
            emptyServiceManager.onStartService()
        }
    }

    fun changeService() {
        if (isServiceRunning()) {
            if (SystemSettingsGpsChecker(context).isEnabled()) {
                emptyServiceManager.onStopService()
                washLocationServiceManager.onStartService()
            } else {
                washLocationServiceManager.onStopService()
                emptyServiceManager.onStartService()
            }
        }
    }

    fun changeServiceState() {
        if (isServiceRunning()) {
            if (serviceSettingsIsDisable()) {
                onStopService()
            }
        } else {
            if (serviceSettingsIsEnable()) {
                onStartService()
            }
        }
    }

    private fun serviceSettingsIsEnable(): Boolean {
        return washSettingsManager.wifiNotify || washSettingsManager.wifiNewDeviceNotify
                || washSettingsManager.wifiEncryptionNotify || washSettingsManager.gpsNotify
    }

    private fun serviceSettingsIsDisable(): Boolean {
        return !serviceSettingsIsEnable()
    }

    fun isNotServiceRunning(): Boolean {
        return !isServiceRunning()
    }

    fun isServiceRunning(): Boolean {
        return emptyServiceManager.isServiceRunning() || washLocationServiceManager.isServiceRunning()
    }

    fun onStopService() {
        if (emptyServiceManager.isServiceRunning())
            emptyServiceManager.onStopService()

        if (washLocationServiceManager.isServiceRunning())
            washLocationServiceManager.onStopService()
    }
}