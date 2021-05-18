package com.hands.clean.service

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat.startForegroundService

open class MyServiceManager<T>(
    private val context: Context,
    private val serviceClass: Class<T>
) {
    open fun onStartService() {
        if (isNotServiceRunning()) {
            val intent = Intent(context, serviceClass)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(context, intent)
            } else {
                context.startService(intent)
            }
        }
    }

    fun isNotServiceRunning(): Boolean {
        return !isServiceRunning()
    }

    fun isServiceRunning(): Boolean {
        val manager: ActivityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        for (service : ActivityManager.RunningServiceInfo in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name.equals(service.service.className)) {
                return true;
            }
        }
        return false;
    }

    fun onStopService() {
        val intent = Intent(context, serviceClass)
        context.stopService(intent)
    }
}