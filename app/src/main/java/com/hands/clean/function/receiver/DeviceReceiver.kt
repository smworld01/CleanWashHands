package com.hands.clean.function.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hands.clean.function.notification.type.NotifyType
import com.hands.clean.function.room.DB
import kotlin.concurrent.thread

const val ACTION_REGISTER_NOTIFICATION_DEVICE: String = "com.hands.clean.ACTION_REGISTER_DEVICE"

val deviceReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action: String = intent.action!!
        when(action) {
            ACTION_REGISTER_NOTIFICATION_DEVICE  -> {
                registerNotificationDevice(context, intent)
            }
        }
    }

    fun registerNotificationDevice(context: Context, intent: Intent) {
        thread {
            val address = intent.getStringExtra("address")!!
            val type = intent.getStringExtra("type")!!
            if(type == NotifyType.Bluetooth.channelId)
                DB.getInstance().bluetoothDao().changeNotificationByAddress(address, true)
            else if(type == NotifyType.Wifi.channelId)
                DB.getInstance().wifiDao().changeNotificationByAddress(address, true)
        }
    }
}