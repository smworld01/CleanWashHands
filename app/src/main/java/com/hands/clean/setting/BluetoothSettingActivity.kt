package com.hands.clean.setting

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.hands.clean.R
import com.hands.clean.room.AppDatabase
import com.hands.clean.room.BluetoothEntry
import com.hands.clean.setting.adapter.RecyclerDeviceAdapter
import com.hands.clean.setting.adapter.RecyclerDeviceData
import com.hands.clean.setting.adapter.adaptRecyclerDevice

class BluetoothSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_setting)

        initLayout()
    }

    private fun initLayout() {
        initActionBar()
        val switchBluetooth: SwitchCompat = findViewById(R.id.switchBluetooth)

        val recyclerViewBluetooth: RecyclerView = findViewById(R.id.recyclerViewBluetooth)
        val deviceList = getBluetoothRecyclerDataArrayList()
        adaptRecyclerDevice(this, recyclerViewBluetooth, deviceList)
        val textViewEmptyRecycler: TextView = findViewById(R.id.textViewEmptyRecycler)
        if(deviceList.isEmpty()) {
            textViewEmptyRecycler.visibility = View.VISIBLE
        } else {
            textViewEmptyRecycler.visibility = View.GONE
        }



        val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "database-name"
                ).build()

        switchBluetooth.setOnCheckedChangeListener{ compoundButton: CompoundButton, isChecked: Boolean ->
            // Todo control recycler
            Thread {
                if (isChecked) {
                    db.bluetoothDao().insertAll(BluetoothEntry(0, "기가지니", "", true))
                    Log.e("test", db.bluetoothDao().getAll()[0].toString())
                } else {
                    Log.e("test", db.bluetoothDao().getAll().toString())
                    db.bluetoothDao().delete(db.bluetoothDao().findByName("기가지니"))
                    Log.e("test", db.bluetoothDao().getAll().toString())
                }
            }.start()
        }
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

    private fun getBluetoothRecyclerDataArrayList(): ArrayList<RecyclerDeviceData> {
        val mBluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        val pairedDevices: Set<BluetoothDevice> = mBluetoothAdapter.bondedDevices;


        return ArrayList<RecyclerDeviceData>(pairedDevices.map { RecyclerDeviceData(it.name, it.address) })
    }

}