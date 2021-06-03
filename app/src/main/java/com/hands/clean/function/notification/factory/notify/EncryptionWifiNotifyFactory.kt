package com.hands.clean.function.notification.factory.notify

import android.app.Notification
import android.content.Context
import com.hands.clean.function.notification.NotificationIdCounter
import com.hands.clean.function.notification.factory.notification.EncryptionWifiNotificationBuilder
import com.hands.clean.function.notification.notify.BasicNotify
import com.hands.clean.function.notification.notify.Notify
import com.hands.clean.function.notification.notify.RecordNotify
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.*
import java.text.SimpleDateFormat
import java.util.*

class EncryptionWifiNotifyFactory(
    private val context: Context,
    private val wifiEntry: WifiEntry
) : WashNotifyFactory(context, wifiEntry) {
    private val notificationId = NotificationIdCounter.getNotificationId()

    override fun createNotification(): Notification {
        val notificationFactory = EncryptionWifiNotificationBuilder(context, wifiEntry, notificationId)
        return notificationFactory.build()
    }
}