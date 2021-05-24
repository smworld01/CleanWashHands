package com.hands.clean.receiver

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hands.clean.function.notification.notify.location.BluetoothNotify
import kotlin.concurrent.thread

val bluetoothReceiver = object : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action!!) {
            BluetoothDevice.ACTION_ACL_CONNECTED -> {
                connectBluetooth(context, intent)
            }
        }
    }

    private fun connectBluetooth(context: Context, intent: Intent) {
        thread {
            BluetoothNotify(context, intent).onNotify()
        }
    }
}