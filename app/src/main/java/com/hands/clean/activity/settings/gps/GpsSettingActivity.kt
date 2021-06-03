package com.hands.clean.activity.settings.gps

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import androidx.appcompat.widget.SwitchCompat
import com.hands.clean.R
import com.hands.clean.activity.settings.TrackerListActivity
import com.hands.clean.function.gps.SystemSettingsGpsManager
import com.hands.clean.function.notification.type.NotifyType
import com.hands.clean.function.permission.GpsPermissionRequesterWithBackground
import com.hands.clean.service.WashLocationServiceManager
import com.hands.clean.function.settings.WashSettingsManager
import com.hands.clean.view.CustomSwitch
import com.hands.clean.view.CustomTextViewDescription

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
        val switchGps = findViewById<CustomSwitch>(R.id.switchGPS).switch

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
        val buttonMap = findViewById<CustomTextViewDescription>(R.id.buttonMap)
        buttonMap.setOnClickListener {
            val intent = Intent(applicationContext, MapsActivity::class.java)
            startActivity(intent)
        }


        val buttonTrackerList = findViewById<CustomTextViewDescription>(R.id.buttonTrackerList)
        buttonTrackerList.setOnClickListener {
            val intent = Intent(applicationContext, TrackerListActivity::class.java)
            intent.putExtra("type", NotifyType.GPS.channelId)
            startActivity(intent)
        }
    }
}