package com.hands.clean.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.RemoteInput
import com.hands.clean.function.notification.factory.notify.NewDeviceRegisterNotifyFactory
import com.hands.clean.function.room.DB
import kotlin.concurrent.thread

class DeviceRegisterReceiver : BroadcastReceiver() {
    override fun onReceive(context:  Context, intent: Intent) {
        registerNotificationDevice(context, intent)
    }

    private fun registerNotificationDevice(context: Context, intent: Intent) {
        thread {
            val remoteReply = RemoteInput.getResultsFromIntent(intent)
            val name = remoteReply.getCharSequence("deviceName")

            val address = intent.getStringExtra("address")!!
            val type = intent.getStringExtra("type")!!
            val notificationId = intent.getIntExtra("notificationId", 0)

            var deviceEntry = DB.getInstance().matchDaoByChannelId(type).findByAddress(address)
            if (deviceEntry != null) {
                deviceEntry.name = name.toString()
                deviceEntry.isNotification = true
                DB.getInstance().matchDaoByChannelId(type).updateAll(deviceEntry)

                NewDeviceRegisterNotifyFactory(context, deviceEntry, notificationId).onBuild().onNotify()
            }

        }
    }
}