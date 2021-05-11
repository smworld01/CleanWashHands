package com.hands.clean.function.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.RemoteInput
import com.hands.clean.function.notification.factory.notify.NewDeviceRegisterNotifyFactory
import com.hands.clean.function.notification.type.NotifyType
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entrys.BluetoothEntry
import com.hands.clean.function.room.entrys.DeviceEntry
import com.hands.clean.function.room.entrys.WifiEntry
import kotlin.concurrent.thread

const val ACTION_REGISTER_NOTIFICATION_DEVICE: String = "com.hands.clean.ACTION_REGISTER_DEVICE"

val deviceRegisterReceiver = object : BroadcastReceiver() {
    override fun onReceive(context:  Context, intent: Intent) {
        val action: String = intent.action!!
        when(action) {
            ACTION_REGISTER_NOTIFICATION_DEVICE  -> {
                registerNotificationDevice(context, intent)
            }
        }
    }

    private fun registerNotificationDevice(context: Context, intent: Intent) {
        thread {
            val remoteReply = RemoteInput.getResultsFromIntent(intent)
            val deviceName = remoteReply.getCharSequence("deviceName")

            val address = intent.getStringExtra("address")!!
            val type = intent.getStringExtra("type")!!
            val notificationId = intent.getIntExtra("notificationId", 0)

            DB.getInstance().matchDaoByChannelId(type).changeNotificationByAddress(address, true)
            DB.getInstance().matchDaoByChannelId(type).changeNameByAddress(address, deviceName.toString())

            NewDeviceRegisterNotifyFactory(context, deviceName.toString(), type, notificationId).onBuild().onNotify()
        }
    }
}