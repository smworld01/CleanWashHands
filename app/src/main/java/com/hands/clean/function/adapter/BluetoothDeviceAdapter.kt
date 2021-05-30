package com.hands.clean.function.adapter

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hands.clean.R
import com.hands.clean.activity.settings.bluetooth.BluetoothRegisterViewModel
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.BluetoothEntry

class BluetoothDeviceAdapter(private val viewModel: BluetoothRegisterViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    private val bluetoothList: List<BluetoothDevice> = getBluetoothDeviceInSystem()

    private fun getBluetoothDeviceInSystem(): List<BluetoothDevice> {
        val mBluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        return mBluetoothAdapter.bondedDevices.toList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecyclerItem(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recycler_item_bluetooth_register, parent, false)
            , viewModel
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recyclerItem: RecyclerItem = holder as RecyclerItem
        recyclerItem.bind(bluetoothList[position])
    }

    override fun getItemCount(): Int {
        return bluetoothList.size
    }

    class RecyclerItem(itemView: View, private val viewModel: BluetoothRegisterViewModel) : RecyclerView.ViewHolder(itemView) {
        private val deviceName: TextView = itemView.findViewById(R.id.deviceName)
        private val registerButton: Button = itemView.findViewById(R.id.button)

        fun bind (data: BluetoothDevice) {
            deviceName.text = data.name

            registerButton.setOnClickListener {
                val findBluetoothEntry = DB.getInstance().bluetoothDao().findByAddress(data.address)
                if(findEntryIsExist(findBluetoothEntry)) {
                    Toast.makeText(
                        itemView.context, "이미 등록된 블루투스 기기입니다.", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.createBluetoothEntry.value = BluetoothEntry(
                        0,
                        "",
                        data.name,
                        data.address,
                        true
                    )
                }
            }
        }
        private fun findEntryIsExist(findEntry: BluetoothEntry?): Boolean {
            return findEntry != null && findEntry.name.isNotEmpty()
        }
    }
}