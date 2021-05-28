package com.hands.clean.service


import android.content.Intent
import androidx.lifecycle.LifecycleService
import com.hands.clean.function.gps.LocationInfo
import com.hands.clean.function.gps.LocationRequester
import com.hands.clean.function.gps.geofencing.WashGeofencing
import com.hands.clean.function.notification.factory.notification.ForegroundNotificationBuilder
import com.hands.clean.function.notification.type.NotifyType
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.LocationEntryBuilder

class WashLocationService: LifecycleService() {
    private lateinit var wifiConnectionChecker: WifiConnectionChecker
    private lateinit var locationRequester: LocationRequester

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        DB.init(applicationContext)
        WashGeofencing.init(applicationContext)

        initWifi(intent)
        initGps()

        DB.getInstance().gpsDao().getAllByLiveData().observe(this) {
            WashGeofencing.getInstance().initGeofence(it)
        }

        registerNotification()

        return START_STICKY
    }

    private fun initWifi(intent: Intent?) {
        if(!::wifiConnectionChecker.isInitialized) {
            wifiConnectionChecker = WifiConnectionChecker(applicationContext, intent)
        }
        wifiConnectionChecker.register()
    }

    private fun initGps() {
        if (!::locationRequester.isInitialized) {
            locationRequester = LocationRequester(applicationContext)
            locationRequester.registerResultCallback { locationResult ->
                val builder = LocationEntryBuilder()
                val locationEntryList = locationResult.locations.map { location ->
                    builder.setLocation(location)
                    builder.build()
                }
                DB.getInstance().locationDao().insertAll(*locationEntryList.toTypedArray())

                if (DB.getInstance().locationDao().getCount() > 100) {
                    DB.getInstance().locationDao().deleteOverData()
                }

                LocationInfo.latitude = locationResult.locations[0].latitude
                LocationInfo.longitude = locationResult.locations[0].longitude
            }
        }
        locationRequester.onRequest()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)

        wifiConnectionChecker.unregister()

        locationRequester.onPause()

        LocationInfo.onRemove()

        WashGeofencing.getInstance().onStop()
    }

    private fun registerNotification() {
        val foregroundNotification =
            ForegroundNotificationBuilder(applicationContext, NotifyType.NoticeService).build()
        startForeground(1, foregroundNotification)
    }
}