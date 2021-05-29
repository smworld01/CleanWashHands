package com.hands.clean.activity

import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
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
import com.hands.clean.R
import com.hands.clean.function.gps.geofencing.WashGeofencing
import com.hands.clean.function.notification.init.NotificationChannelManager
import com.hands.clean.receiver.bluetoothReceiver
import com.hands.clean.function.room.DB
import com.hands.clean.function.settings.WashSettingsManager
import com.hands.clean.receiver.ServiceStopReceiver
import com.hands.clean.service.TrackerServiceManager

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

        val gpsFilter = IntentFilter()
        gpsFilter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION)

        val gpsReceiver = ServiceStopReceiver()
        registerReceiver(gpsReceiver, gpsFilter)
    }
    private fun initService() {
        if (washSettings.wifiNotify || washSettings.gpsNotify) {
            val ws = TrackerServiceManager(this)
            ws.onStartService()
        }
    }
}