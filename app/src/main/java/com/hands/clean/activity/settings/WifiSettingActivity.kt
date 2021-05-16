package com.hands.clean.activity.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.function.permission.GpsPermissionRequesterWithBackground
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.DeviceEntry
import com.hands.clean.function.settings.WashSettingsManager
import com.hands.clean.activity.settings.adapter.adaptRecyclerDevice
import com.hands.clean.function.room.entry.LocationEntry
import com.hands.clean.function.service.WashLocationServiceManager
import kotlin.concurrent.thread

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

        initRecyclerView()
    }

    private fun initActionBar() {
        supportActionBar?.title = "와이파이 설정"
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

    private fun initSwitchViews() {
        val switchWifi: SwitchCompat = findViewById(R.id.switchWifi)
        val switchNewDeviceWifi: SwitchCompat = findViewById(R.id.switchNewDeviceWifi)
        val switchEncryptionWifi: SwitchCompat = findViewById(R.id.switchEncryptionWifi)

        switchWifi.isChecked = settings.wifiNotify
        switchNewDeviceWifi.isChecked = settings.wifiNewDeviceNotify
        switchEncryptionWifi.isChecked = settings.wifiEncryptionNotify


        switchWifi.setOnCheckedChangeListener{ _, isChecked ->
            settings.wifiNotify = isChecked
            val wsm = WashLocationServiceManager(this)
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

    private fun initRecyclerView() {
        val recyclerViewWifi: RecyclerView = findViewById(R.id.recyclerViewWifi)
        val textViewEmptyRecycler: TextView = findViewById(R.id.textViewEmptyRecycler)

        val mAdapter = adaptRecyclerDevice(this, recyclerViewWifi)

        DB.getInstance().wifiDao().getAllByLiveData().observe(this) {
            mAdapter.submitList(it as List<LocationEntry>?)
            if (it.isEmpty()) {
                textViewEmptyRecycler.visibility = View.VISIBLE
            } else {
                textViewEmptyRecycler.visibility = View.GONE
            }
        }
    }
}