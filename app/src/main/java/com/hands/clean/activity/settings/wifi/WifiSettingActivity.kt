package com.hands.clean.activity.settings.wifi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.widget.SwitchCompat
import com.hands.clean.R
import com.hands.clean.activity.settings.TrackerListActivity
import com.hands.clean.function.permission.GpsPermissionRequesterWithBackground
import com.hands.clean.function.settings.WashSettingsManager
import com.hands.clean.function.notification.type.NotifyType
import com.hands.clean.service.TrackerServiceManager

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
        supportActionBar?.title = "와이파이 설정"
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
        val switchWifi: SwitchCompat = findViewById(R.id.switchWifi)
        val switchNewDeviceWifi: SwitchCompat = findViewById(R.id.switchNewDeviceWifi)
        val switchEncryptionWifi: SwitchCompat = findViewById(R.id.switchEncryptionWifi)

        switchWifi.isChecked = settings.wifiNotify
        switchNewDeviceWifi.isChecked = settings.wifiNewDeviceNotify
        switchEncryptionWifi.isChecked = settings.wifiEncryptionNotify


        switchWifi.setOnCheckedChangeListener{ _, isChecked ->
            settings.wifiNotify = isChecked
            val wsm = TrackerServiceManager(this)
            // Todo waiting
            if (isChecked)
                wsm.onStartService()
            else if (!settings.gpsNotify)
                wsm.onStopService()
        }
        switchNewDeviceWifi.setOnCheckedChangeListener { _, isChecked ->
            settings.wifiNewDeviceNotify = isChecked
        }
        switchEncryptionWifi.setOnCheckedChangeListener { _, isChecked ->
            settings.wifiEncryptionNotify = isChecked
        }
    }

    private fun initButtons() {
        val buttonTrackerList: Button = findViewById(R.id.buttonTrackerList)
        val buttonWifiScan: Button = findViewById(R.id.buttonWifiScan)

        buttonTrackerList.setOnClickListener {
            val intent = Intent(this, TrackerListActivity::class.java)
            intent.putExtra("type", NotifyType.Wifi.channelId)
            startActivity(intent)
        }
        buttonWifiScan.setOnClickListener {
            startActivity(Intent(this, WifiScanActivity::class.java))
        }
    }
}