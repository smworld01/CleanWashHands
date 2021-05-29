package com.hands.clean.service


import android.content.Intent
import androidx.lifecycle.LifecycleService
import com.hands.clean.function.gps.LocationInfo
import com.hands.clean.function.gps.LocationRequester
import com.hands.clean.function.gps.SystemSettingsGpsChecker
import com.hands.clean.function.gps.geofencing.WashGeofencing
import com.hands.clean.function.notification.factory.notification.ForegroundNotificationBuilder
import com.hands.clean.function.notification.factory.notification.GpsPauseNotificationBuilder
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.LocationEntryBuilder

class EmptyService: LifecycleService() {
    private lateinit var wifiConnectionChecker: WifiConnectionChecker
    private lateinit var locationRequester: LocationRequester

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        registerNotification()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }

    private fun registerNotification() {
        val foregroundNotification =
            GpsPauseNotificationBuilder(applicationContext).build()
        startForeground(1, foregroundNotification)
    }
}