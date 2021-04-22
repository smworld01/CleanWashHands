package com.hands.clean.function.receiver

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.hands.clean.function.notification.BluetoothNotify
import com.hands.clean.function.notification.NewDeviceNotify
import com.hands.clean.function.room.BluetoothEntry
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
        val db = useDatabase(context)
        val device: BluetoothDevice =
                intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)!!

        thread {
            val queryDevice: BluetoothEntry? =
                    db.bluetoothDao().findByAddress(device.address)

            if (queryDevice == null) {
                db.bluetoothDao().insertAll(BluetoothEntry(0, device.name, device.address, false))
                NewDeviceNotify(context)
                        .setNotification("연결하시겠습니까?")
                        // .addRegisterDeviceAction(device.address)
                        .sendNotification()
            } else {
                if (queryDevice.isNotification) {
                    BluetoothNotify(context)
                            .setNotification("${device.name}에 연결되었습니다.")
                            .sendNotification()
                }
            }

            db.close()
        }

    }
}