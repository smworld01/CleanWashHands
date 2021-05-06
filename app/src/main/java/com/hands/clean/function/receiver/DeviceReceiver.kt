package com.hands.clean.function.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.RemoteInput
import com.hands.clean.function.notification.type.NotifyType
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entrys.BluetoothEntry
import com.hands.clean.function.room.entrys.DeviceEntry
import com.hands.clean.function.room.entrys.WifiEntry
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
            val remoteReply = RemoteInput.getResultsFromIntent(intent)
            val deviceName = remoteReply.getCharSequence("deviceName")
            Log.e("test", deviceName.toString())

            val address = intent.getStringExtra("address")!!
            val type = intent.getStringExtra("type")!!
            val notificationId = intent.getIntExtra("notificationId", 0)
            when (type) {
                NotifyType.Wifi.channelId -> DB.getInstance().wifiDao().changeNotificationByAddress(address, true)
                NotifyType.Bluetooth.channelId -> DB.getInstance().bluetoothDao().changeNotificationByAddress(address, true)
                else -> throw Exception()
            }

            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(notificationId)
        }
    }
}