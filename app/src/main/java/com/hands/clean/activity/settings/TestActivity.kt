package com.hands.clean.activity.settings

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.hands.clean.R
import com.hands.clean.function.gps.geofencing.WashGeofencing
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.GpsEntry
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
        }

        buttonChangeNotificationChannel.setOnClickListener {
//            WashNotifyFactory(applicationContext, WifiEntry(0, "1", "2", false)).onBuild().onNotify()
        }
    }
}