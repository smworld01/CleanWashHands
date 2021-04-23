package com.hands.clean

import android.bluetooth.BluetoothDevice
import android.content.IntentFilter
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
import com.hands.clean.function.notification.*
import com.hands.clean.function.receiver.ACTION_REGISTER_NOTIFICATION_DEVICE
import com.hands.clean.function.receiver.bluetoothReceiver
import com.hands.clean.function.receiver.deviceReceiver
import com.hands.clean.function.room.db
import com.hands.clean.function.room.useDatabase

class NavigationActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
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
            R.id.nav_home, R.id.nav_settings
        ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        initNotification()
        initReceiver()
        db = useDatabase(applicationContext)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun initNotification() {
        BluetoothNotify(this).initChannel()
        WifiNotify(this).initChannel()
        GPSNotify(this).initChannel()
    }

    private  fun initReceiver() {
        val bluetoothFilter = IntentFilter()
        bluetoothFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)

        registerReceiver(bluetoothReceiver, bluetoothFilter)

        val deviceFilter = IntentFilter()
        deviceFilter.addAction(ACTION_REGISTER_NOTIFICATION_DEVICE)

        registerReceiver(deviceReceiver, deviceFilter)
    }
}