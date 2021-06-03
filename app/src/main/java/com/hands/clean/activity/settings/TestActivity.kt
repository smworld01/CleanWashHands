package com.hands.clean.activity.settings

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.hands.clean.R
import com.hands.clean.function.gps.geofencing.WashGeofencing
import com.hands.clean.function.notification.factory.notify.WashNotifyFactory
import com.hands.clean.function.notification.type.NotifyType
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
            WashNotifyFactory(this, WifiEntry(0, "123","123","123"))
                .onBuild()
                .onNotify()
        }

        buttonChangeNotificationChannel.setOnClickListener {
            val summaryNotification = NotificationCompat.Builder(this, NotifyType.Bluetooth.channelId)
                .setContentTitle("손을 씻어주세요.")
                //set content text to support devices running API level < 24
                .setContentText("손 씻기 알림")
                .setSmallIcon(R.drawable.ic_baseline_home_24)
                //build summary info into InboxStyle template
                //specify which group this notification belongs to
                .setGroup("com.hands.clean.WASH_NOTIFY")
                //set this notification as the summary for the group
                .setGroupSummary(true)
                .setOnlyAlertOnce(true)
                .build()
            NotificationManagerCompat.from(this).apply {
                notify(2, summaryNotification)
            }
        }
    }
}