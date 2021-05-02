package com.hands.clean.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entrys.DeviceEntry
import com.hands.clean.function.settings.WashSettingsManager
import com.hands.clean.setting.adapter.RecyclerDeviceData
import com.hands.clean.setting.adapter.adaptRecyclerDevice
import kotlin.concurrent.thread

class WifiSettingActivity : AppCompatActivity() {
    val settings = WashSettingsManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi_setting)

        initLayout()
    }

    private fun initLayout() {
        thread {
            initActionBar()

            initSwitchViews()

            initRecyclerView()
        }
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

            val registeredWifiList: List<DeviceEntry> = DB.getInstance().wifiDao().getAll()

            val deviceList: ArrayList<DeviceEntry> = registeredWifiList as ArrayList<DeviceEntry>

            adaptRecyclerDevice(this, recyclerViewWifi, deviceList)


            val textViewEmptyRecycler: TextView = findViewById(R.id.textViewEmptyRecycler)
            if(deviceList.isEmpty()) {
                textViewEmptyRecycler.visibility = View.VISIBLE
            } else {
                textViewEmptyRecycler.visibility = View.GONE
            }
    }
}