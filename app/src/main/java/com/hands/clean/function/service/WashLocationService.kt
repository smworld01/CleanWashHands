package com.hands.clean.function.service

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.hands.clean.function.gps.LocationInfo
import com.hands.clean.function.gps.LocationRequester
import com.hands.clean.function.gps.geofencing.WashGeofencing
import com.hands.clean.function.notification.factory.notification.ForegroundNotificationFactory
import com.hands.clean.function.notification.type.NotifyType

class WashLocationService: Service() {
    private lateinit var wifiConnectionChecker: WifiConnectionChecker
    private lateinit var locationRequester: LocationRequester

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
        throw UnsupportedOperationException("Not yet implemented");
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initWifi(intent)
        }
        initGps()

        WashGeofencing.getInstance().initGeofence()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {    //  LOLLIPOP Version 이상..
            registerNotification()
        }
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initWifi(intent: Intent) {
        if(!::wifiConnectionChecker.isInitialized) {
            wifiConnectionChecker = WifiConnectionChecker(applicationContext, intent);
        }
        wifiConnectionChecker.register();
    }

    private fun initGps() {
        if (!::locationRequester.isInitialized) {
            locationRequester = LocationRequester(applicationContext)
            locationRequester.registerResultCallback {
                Log.e("current Location", it.toString())
                LocationInfo.latitude = it.locations[0].latitude
                LocationInfo.longitude = it.locations[0].longitude
            }
        }
        locationRequester.onRequest()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {     //  LOLLIPOP Version 이상..
            wifiConnectionChecker.unregister();
        }
        locationRequester.onPause()
        LocationInfo.onRemove()

        WashGeofencing.getInstance().onStop()
    }

    private fun registerNotification() {
        val foregroundNotification =
            ForegroundNotificationFactory(applicationContext, NotifyType.NoticeService).onBuild()
        startForeground(1, foregroundNotification);
    }
}