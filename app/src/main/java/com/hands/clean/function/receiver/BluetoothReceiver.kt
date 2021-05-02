package com.hands.clean.function.receiver

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hands.clean.function.notification.NewDeviceNotify
import com.hands.clean.function.notification.WashNotify
import com.hands.clean.function.notification.type.NotifyType
import com.hands.clean.function.room.entrys.BluetoothEntry
import com.hands.clean.function.room.DB
import com.hands.clean.function.settings.WashSettingsManager
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
        val settings = WashSettingsManager(context)
        if (!settings.bluetoothNotify) return
        DB.getInstance(context)
        val device: BluetoothDevice = getDevice(intent)

        thread {
            val queryDevice: BluetoothEntry? = findDeviceInDB(device.address)

            if (isNewDevice(queryDevice)) {
                if (!settings.bluetoothNewDeviceNotify) return@thread
                registerDeviceInDB(device)
                askUserForNewDeviceIsNotification(context, device)
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
        return DB.getInstance().bluetoothDao().findByAddress(address)
    }
    private fun isNewDevice(device : BluetoothEntry?): Boolean {
        return device == null
    }
    private fun registerDeviceInDB(device: BluetoothDevice) {
        DB.getInstance().bluetoothDao().insertAll(BluetoothEntry(0, device.name, device.address, false))
    }
    private fun askUserForNewDeviceIsNotification(context:Context, device: BluetoothDevice) {
        NewDeviceNotify(context, NotifyType.Bluetooth).send(device.name)
    }
    private fun updateDeviceName(device: BluetoothDevice, queryDevice: BluetoothEntry) {
        if(isNameChanged(device, queryDevice)) {
            DB.getInstance().bluetoothDao().changeNameByAddress(device.address, device.name)
        }
    }
    private fun isNameChanged(device: BluetoothDevice, queryDevice: BluetoothEntry): Boolean {
        return device.name != queryDevice.name
    }
    private fun sendNotification(context: Context, name: String) {
        WashNotify(context, NotifyType.Bluetooth).send("블루투스 기기 ${name}에 연결되었습니다.")
    }
}