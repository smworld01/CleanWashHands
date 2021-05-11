package com.hands.clean

import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.hands.clean.activity.TutorialActivity
import com.hands.clean.function.gps.geofencing.WashGeofencing
import com.hands.clean.function.notification.init.NotificationChannelManager
import com.hands.clean.function.receiver.ACTION_REGISTER_NOTIFICATION_DEVICE
import com.hands.clean.function.receiver.bluetoothReceiver
import com.hands.clean.function.receiver.deviceRegisterReceiver
import com.hands.clean.function.room.DB
import com.hands.clean.function.service.WashLocationService
import com.hands.clean.function.service.WashLocationServiceManager
import com.hands.clean.function.settings.WashSettingsManager

class NavigationActivity : AppCompatActivity() {
    private val washSettings = WashSettingsManager(this)
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        DB.init(this)
        WashGeofencing.init(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_settings, R.id.nav_logs
        ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        initNotification()
        initReceiver()
        initService()

        val settings = WashSettingsManager(applicationContext)
        if (!settings.tutorial) {
            startActivity(Intent(this, TutorialActivity::class.java))
            settings.tutorial=true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun initNotification() {
        NotificationChannelManager(this).initChannel()
    }

    private  fun initReceiver() {
        val bluetoothFilter = IntentFilter()
        bluetoothFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)

        registerReceiver(bluetoothReceiver, bluetoothFilter)

        val deviceFilter = IntentFilter()
        deviceFilter.addAction(ACTION_REGISTER_NOTIFICATION_DEVICE)

        registerReceiver(deviceRegisterReceiver, deviceFilter)

    }
    private fun initService() {
        if (washSettings.wifiNotify || washSettings.gpsNotify) {
            val ws = WashLocationServiceManager(this)
            ws.onStartService()
        }
    }
}