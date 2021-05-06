package com.hands.clean.activity.settings

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.function.room.entrys.BluetoothEntry
import com.hands.clean.function.room.entrys.DeviceEntry
import com.hands.clean.function.settings.WashSettingsManager
import com.hands.clean.activity.settings.adapter.adaptRecyclerDevice

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

        initRecyclerView()
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

    private fun initRecyclerView() {
        val recyclerViewBluetooth: RecyclerView = findViewById(R.id.recyclerViewBluetooth)
        val deviceList = getBluetoothRecyclerDataArrayList()
        adaptRecyclerDevice(this, recyclerViewBluetooth, deviceList)
        val textViewEmptyRecycler: TextView = findViewById(R.id.textViewEmptyRecycler)
        if(deviceList.isEmpty()) {
            textViewEmptyRecycler.visibility = View.VISIBLE
        } else {
            textViewEmptyRecycler.visibility = View.GONE
        }
    }

    private fun getBluetoothRecyclerDataArrayList(): ArrayList<DeviceEntry> {
        val mBluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        val pairedDevices: Set<BluetoothDevice> = mBluetoothAdapter.bondedDevices;


        return ArrayList<DeviceEntry>(pairedDevices.map { BluetoothEntry(0, it.name, it.address, false) })
    }

}