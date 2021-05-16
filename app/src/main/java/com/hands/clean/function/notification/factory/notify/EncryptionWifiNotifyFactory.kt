package com.hands.clean.function.notification.factory.notify

import android.content.Context
import com.hands.clean.function.notification.NotificationIdCounter
import com.hands.clean.function.notification.factory.notification.EncryptionWifiNotificationBuilder
import com.hands.clean.function.notification.notify.Notify
import com.hands.clean.function.notification.notify.RecordNotify
import com.hands.clean.function.room.entry.*

class EncryptionWifiNotifyFactory(
    private val context: Context,
    private val wifiEntry: WifiEntry
) : NotifyFactory {
    private val notificationId = NotificationIdCounter.getNotificationId()

    override fun onBuild(): Notify {
        val notificationFactory = EncryptionWifiNotificationBuilder(context, wifiEntry, notificationId)
        val notification = notificationFactory.build()

        val washEntry = notificationFactory.onBuildWashEntry()

        return RecordNotify(context, notification, washEntry, notificationId)
    }
}