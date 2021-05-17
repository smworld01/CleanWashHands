package com.hands.clean.activity.settings

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.widget.SwitchCompat
import com.hands.clean.R
import com.hands.clean.function.room.entry.BluetoothEntry
import com.hands.clean.function.settings.WashSettingsManager
import com.hands.clean.function.notification.type.NotifyType
import com.hands.clean.function.room.entry.TrackerEntry

class BluetoothSettingActivity : AppCompatActivity() {
    private val settings = WashSettingsManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_setting)

        initLayout()
    }

    private fun initLayout() {
        initActionBar()

        initSwitchViews()

        initButtons()
    }

    private fun initActionBar() {
        supportActionBar?.title = "블루투스 설정"
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
        val switchBluetooth: SwitchCompat = findViewById(R.id.switchBluetooth)
        switchBluetooth.isChecked = settings.bluetoothNotify

        switchBluetooth.setOnCheckedChangeListener{ _, isChecked: Boolean ->
            settings.bluetoothNotify = isChecked
        }

        val switchNewDeviceBluetooth: SwitchCompat = findViewById(R.id.switchNewDeviceBluetooth)
        switchNewDeviceBluetooth.isChecked = settings.bluetoothNewDeviceNotify

        switchNewDeviceBluetooth.setOnCheckedChangeListener{ _, isChecked: Boolean ->
            settings.bluetoothNewDeviceNotify = isChecked
        }
    }

    private fun initButtons() {
        val buttonTrackerList: Button = findViewById(R.id.buttonTrackerList)

        buttonTrackerList.setOnClickListener {
            val intent = Intent(applicationContext, TrackerListActivity::class.java)
            intent.putExtra("type", NotifyType.Bluetooth.channelId)
            startActivity(intent)
        }
    }
}