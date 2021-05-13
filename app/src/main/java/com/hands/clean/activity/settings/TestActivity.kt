package com.hands.clean.activity.settings

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.gms.location.*
import com.hands.clean.R
import com.hands.clean.function.gps.geofencing.WashGeofencing
import com.hands.clean.function.notification.factory.notify.WashNotifyFactory
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.GpsEntry
import com.hands.clean.function.room.entry.WifiEntry
import kotlin.concurrent.thread

class TestActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        initLayout()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
            }
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun initLayout() {
        val buttonSendNotification : Button = findViewById(R.id.buttonSendNotification)
        val buttonChangeNotificationChannel : Button = findViewById(R.id.buttonChangeNotificationChannel)

        buttonSendNotification.setOnClickListener {
            fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location ->
                val entry = GpsEntry(0, "home", "test", location.latitude, location.longitude, 50f, true)
                Log.e("test",entry.toString())
                thread {
                    DB.getInstance().gpsDao().insertAll(entry)
                    WashGeofencing.getInstance().initGeofence()
                }
            }
        }

        buttonChangeNotificationChannel.setOnClickListener {
            WashNotifyFactory(applicationContext, WifiEntry(0, "1", "2", false)).onBuild().onNotify()
        }
    }
}