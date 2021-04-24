package com.hands.clean.function.receiver

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.hands.clean.function.room.BluetoothEntry
import com.hands.clean.function.room.db
import com.hands.clean.function.room.useDatabase
import kotlin.concurrent.thread

const val ACTION_REGISTER_NOTIFICATION_DEVICE: String = "com.hands.clean.ACTION_REGISTER_DEVICE"

val deviceReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action: String = intent.action!!
        db = useDatabase(context)
        when(action) {
            ACTION_REGISTER_NOTIFICATION_DEVICE  -> {
                registerNotificationDevice(context, intent)
            }
        }
    }

    fun registerNotificationDevice(context: Context, intent: Intent) {
        val address = intent.getStringExtra("address")!!
        db.bluetoothDao().changeNotificationByAddress(address, true)

        db.close()
    }
}