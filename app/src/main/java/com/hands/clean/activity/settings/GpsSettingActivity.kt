package com.hands.clean.activity.settings

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.activity.settings.adapter.adaptRecyclerDevice
import com.hands.clean.function.gps.SystemSettingsGpsManager
import com.hands.clean.function.permission.GpsPermissionRequesterWithBackground
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.LocationEntry
import com.hands.clean.function.service.WashLocationServiceManager
import com.hands.clean.function.settings.WashSettingsManager
import kotlin.concurrent.thread

class GpsSettingActivity : AppCompatActivity(){
    private val permissionRequester = GpsPermissionRequesterWithBackground(this)
    private val gpsSetting: SystemSettingsGpsManager = SystemSettingsGpsManager(this)
    private val settings = WashSettingsManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActionBar()

        permissionRequester.registerGranted { gpsSetting.onRequest() }
        permissionRequester.registerDenied { finish() }
        permissionRequester.onRequest()

        setContentView(R.layout.activity_gps_setting)

        initSwitch()
        initButton()
        initRecyclerView()
    }
    private fun initActionBar() {
        supportActionBar?.title = "GPS 설정"
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
    }
    // 상단바 뒤로가기 버튼 이벤트
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initSwitch() {
        val switchGps = findViewById<SwitchCompat>(R.id.switchGPS)

        switchGps.isChecked = settings.gpsNotify

        switchGps.setOnCheckedChangeListener{ _, isChecked ->
            settings.gpsNotify = isChecked
            val wsm = WashLocationServiceManager(this)
            // Todo waiting
            if (isChecked)
                wsm.onStartService()
            else if (!settings.wifiNotify)
                wsm.onStopService()
        }
    }

    private fun initButton() {
        val buttonMap = findViewById<Button>(R.id.buttonMap)
        buttonMap.setOnClickListener {
            val intent = Intent(applicationContext, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        thread {
            val recyclerViewGps: RecyclerView = findViewById(R.id.recyclerViewGps)

            val locationList: List<LocationEntry> = DB.getInstance().gpsDao().getAll()

            adaptRecyclerDevice(this, recyclerViewGps, locationList)


            val textViewEmptyRecycler: TextView = findViewById(R.id.textViewEmptyRecycler)
            if (locationList.isEmpty()) {
                textViewEmptyRecycler.visibility = View.VISIBLE
            } else {
                textViewEmptyRecycler.visibility = View.GONE
            }
        }
    }
}