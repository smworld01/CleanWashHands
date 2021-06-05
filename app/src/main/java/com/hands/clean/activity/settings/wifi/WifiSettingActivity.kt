package com.hands.clean.activity.settings.wifi

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.hands.clean.R
import com.hands.clean.activity.settings.TrackerListActivity
import com.hands.clean.function.notification.type.NotifyType
import com.hands.clean.function.permission.GpsPermissionRequesterWithBackground
import com.hands.clean.function.settings.WashSettingsManager
import com.hands.clean.service.TrackerServiceManager
import com.hands.clean.view.CustomSwitch
import com.hands.clean.view.CustomTextViewDescription

class WifiSettingActivity : AppCompatActivity() {
    private val permissionRequester = GpsPermissionRequesterWithBackground(this)
    val settings = WashSettingsManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi_setting)

        permissionRequester.onRequest()
        permissionRequester.registerDenied { finish() }
        initLayout()
    }

    private fun initLayout() {
        initActionBar()

        initSwitchViews()

        initButtons()
    }

    private fun initActionBar() {
        supportActionBar?.title = "Wi-Fi 설정"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

    private fun initSwitchViews() {
        val switchWifi = findViewById<CustomSwitch>(R.id.switchWifi).switch
        val switchNewDeviceWifi = findViewById<CustomSwitch>(R.id.switchNewDeviceWifi).switch
        val switchEncryptionWifi = findViewById<CustomSwitch>(R.id.switchEncryptionWifi).switch
        val wsm = TrackerServiceManager(this)

        switchWifi.isChecked = settings.wifiNotify
        switchNewDeviceWifi.isChecked = settings.wifiNewDeviceNotify
        switchEncryptionWifi.isChecked = settings.wifiEncryptionNotify


        switchWifi.setOnCheckedChangeListener{ _, isChecked ->
            settings.wifiNotify = isChecked
            wsm.changeServiceState()
            // Todo waiting
        }
        switchNewDeviceWifi.setOnCheckedChangeListener { _, isChecked ->
            settings.wifiNewDeviceNotify = isChecked
            wsm.changeServiceState()
            // Todo waiting
        }
        switchEncryptionWifi.setOnCheckedChangeListener { _, isChecked ->
            settings.wifiEncryptionNotify = isChecked
            wsm.changeServiceState()
            // Todo waiting
        }
    }

    private fun initButtons() {
        val buttonTrackerList: CustomTextViewDescription = findViewById(R.id.buttonTrackerList)
        val buttonWifiScan: CustomTextViewDescription = findViewById(R.id.buttonWifiScan)
        val buttonDetectedWifi: CustomTextViewDescription = findViewById(R.id.buttonDetectedWifi)

        buttonTrackerList.setOnClickListener {
            val intent = Intent(this, TrackerListActivity::class.java)
            intent.putExtra("type", NotifyType.Wifi.channelId)
            startActivity(intent)
        }
        buttonWifiScan.setOnClickListener {
            startActivity(Intent(this, WifiScanActivity::class.java))
        }
        buttonDetectedWifi.setOnClickListener {
            startActivity(Intent(this, WifiDetectedActivity::class.java))
        }
    }
}