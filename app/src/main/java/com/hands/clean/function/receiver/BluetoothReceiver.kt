package com.hands.clean.function.receiver

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.hands.clean.function.notification.BluetoothNotify
import com.hands.clean.function.notification.NewDeviceNotify
import com.hands.clean.function.room.BluetoothEntry
import com.hands.clean.function.room.db
import com.hands.clean.function.room.useDatabase
import kotlin.concurrent.thread

val bluetoothReceiver = object : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action: String = intent.action!!
        when(action) {
            BluetoothDevice.ACTION_ACL_CONNECTED -> {
                connectBluetooth(context, intent)
            }
        }
    }

    private fun connectBluetooth(context: Context, intent: Intent) {
        db = useDatabase(context)
        val device: BluetoothDevice = getDevice(intent)

        thread {
            val queryDevice: BluetoothEntry? = findDeviceInDB(device.address)

            if (isNewDevice(queryDevice)) {
                registerDeviceInDB(device)
                askUserForNewDeviceIsNotification(context)
            } else {
                updateDeviceName(device, queryDevice!!)

                if (queryDevice.isNotification) {
                    sendNotification(context, device.name)
                }
            }
        }
    }
    private fun getDevice(intent: Intent): BluetoothDevice {
        return intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)!!
    }
    private fun findDeviceInDB(address: String): BluetoothEntry? {
        return db.bluetoothDao().findByAddress(address)
    }
    private fun isNewDevice(device :BluetoothEntry?): Boolean {
        return device == null
    }
    private fun registerDeviceInDB(device: BluetoothDevice) {
        db.bluetoothDao().insertAll(BluetoothEntry(0, device.name, device.address, false))
    }
    private fun askUserForNewDeviceIsNotification(context:Context) {
        NewDeviceNotify(context)
                .setNotification("등록하시겠습니까?")
                // .addRegisterDeviceAction(device.address)
                .send()
    }
    private fun updateDeviceName(device: BluetoothDevice, queryDevice: BluetoothEntry) {
        if(isNameChanged(device, queryDevice)) {
            db.bluetoothDao().changeNameByAddress(device.address, device.name)
        }
    }
    private fun isNameChanged(device: BluetoothDevice, queryDevice: BluetoothEntry): Boolean {
        return device.name != queryDevice.name
    }
    private fun sendNotification(context: Context, name: String) {
        BluetoothNotify(context)
                .setNotification("${name}에 연결되었습니다.")
                .send()
    }
}